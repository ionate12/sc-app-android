package au.com.safetychampion.scmobile.visitorModule

import au.com.safetychampion.scmobile.database.visitor.VisitorActivityDB
import au.com.safetychampion.scmobile.repository.BaseRepository
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.SharedPrefCollection
import au.com.safetychampion.scmobile.visitorModule.models.*
import au.com.safetychampion.scmobile.visitorModule.repository.VisitorLocalRepository
import au.com.safetychampion.scmobile.visitorModule.repository.VisitorNetworkRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

/**
 * Define all interactive use cases for visitor module in this schema.
 */
interface IVisitorRepository {
    // Interacts with back-end server + local storage
    fun getToken(orgId: String, siteId: String, pin: String?): Single<VisitorToken>
    fun getSiteFetch(token: String): Single<VisitorSite>
    fun getFormFetch(token: String, formId: String, site: VisitorSite): Single<VisitorForm>

    /**
     * @param oldList: this list should already filtered to contain only Pending Leave Records.
     * this call will also do update the current evidence records on db.
     */
    fun getVisitFetch(oldList: List<VisitorEvidence>): Single<List<VisitorEvidence>>
    fun performArrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm, site: VisitorSite): Single<VisitorEvidence>
    fun performLeave(leaveForm: VisitorForm?, evidence: VisitorEvidence): Single<VisitorEvidence>

    // Only Local storage.
    fun getProfile(id: String): Single<VisitorProfile>
    fun getProfileAsFlowable(id: String): Flowable<VisitorProfile>
    fun getFullEvidenceById(activityId: String): Single<VisitorEvidence>
    fun getActivitiesByProfileId(profileId: String, isActive: Boolean? = null): Flowable<List<VisitorActivityDB>>
    fun saveProfile(profile: VisitorProfile): Completable
    fun loadSite(id: String): Single<VisitorSite>

    fun setAutoSignOut(evidenceId: String, isAutoSignOut: Boolean): Completable
}

/**
 * Main expose repository.
 */
object VisitorRepository : BaseRepository(), IVisitorRepository {
    private val networkRepo = VisitorNetworkRepository()
    private val localRepo = VisitorLocalRepository()

    override fun getToken(orgId: String, siteId: String, pin: String?): Single<VisitorToken> {
        return sendRequest(networkRepo.token(orgId, siteId, pin))
    }

    override fun getSiteFetch(token: String): Single<VisitorSite> {
        return sendRequest(networkRepo.siteFetch(token))
                //THEN STORE TO DB AND RETURN THE VISITOR SITE AS SINGLE Type
                .flatMap { visitorSite ->
                    //Store to db
                    localRepo.saveFetchSite(visitorSite)
                            .andThen(Single.just(visitorSite))
                }
    }

    override fun getFormFetch(token: String, formId: String, site: VisitorSite): Single<VisitorForm> {
        return sendRequest(networkRepo.formFetch(token, formId)).flatMap { mForm ->
            //add Form To the Current Site.
            for (parentForm in site.forms) {
                if (parentForm.arrive != null && parentForm.arrive.form._id == mForm._id) {
                    parentForm.arrive.form = mForm
                }
                if (parentForm.leave != null && parentForm.leave.form._id == mForm._id) {
                    parentForm.leave.form = mForm
                }
            }
            localRepo.saveFetchSite(site).andThen(Single.just(mForm))
        }
    }

    override fun getVisitFetch(oldList: List<VisitorEvidence>): Single<List<VisitorEvidence>> {
        fun updateDb(fetchedEvidences: List<VisitorEvidence>): Completable {
            val gson = GsonHelper.getGson()
            val ids = fetchedEvidences.map(VisitorEvidence::_id)
            return localRepo.getActivities(ids).map { list ->
                //append new data to db instance
                val mList = list.toMutableList()
                if (fetchedEvidences.size == mList.size) {
                    for (i in fetchedEvidences.indices) {
                        if (fetchedEvidences[i]._id != list[i]._id) {
                            continue
                        }
                        val evidence = fetchedEvidences[i]
                        //retain some necessary data like token, autoSignOut and notifId
                        evidence.setRetainDataWhenUpdateDB(mList[i].toVisitorEvidence())

                        mList[i].data = gson.toJsonTree(evidence).asJsonObject
                        if (fetchedEvidences[i].leave != null) {
                            mList[i].isActive = false
                        }
                    }
                    return@map mList
                } else { //Update List by check id for each items.
                    mList.forEach { item ->
                        fetchedEvidences.find { it._id == item._id }?.let { item.data = gson.toJsonTree(it).asJsonObject }
                    }
                    return@map mList
                }
            }.flatMapCompletable { updatedActivityList -> localRepo.saveActivities(updatedActivityList) }
        }
        //1. fetch first.
        val nonNullTokenList = oldList.filter { it.token != null }
        val tokens = nonNullTokenList.mapNotNull(VisitorEvidence::token)
        var resultList: List<VisitorEvidence>? = null
        return networkRepo.visitFetch(tokens)
                .map {
                    resultList = it.map(VisitorEvidenceWrapper::item)
                    return@map resultList
                }
                .flatMapCompletable { updateDb(it) }.toSingle {
                    return@toSingle resultList!!
                }
    }

    /**
     * Submit function will do the following:
     * 1. Send Submit request to the server. (Offline will perform onError)
     * 2. If Succeeded, save profile and save visitor activity to DB.
     * 3. return the successful result payload.
     */
    override fun performArrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm, site: VisitorSite): Single<VisitorEvidence> {
        return sendRequest(networkRepo.arrive(token, profile, arriveForm)).flatMap { evidence ->
            evidence.token = token
            val jsonObject = GsonHelper.getGson().toJsonTree(evidence.apply { this.site = site }).asJsonObject
            val activityDB = VisitorActivityDB(evidence._id, profile._id, site._id, Date(), VisitorStatus.IN, site.tier.name, site.title, isActive = true, jsonObject, token)

            //Set Shared Pref
            SharedPrefCollection.currentVisitorProfileId = profile._id
            //store profile to DB.
            return@flatMap localRepo.saveProfile(profile)
                    .andThen(localRepo.saveActivity(activityDB))
                    .andThen(Single.just(evidence))
        }
    }

    /**
     * @param evidence: This is according evidence of leave form. Token should be store in this evidence
     */
    override fun performLeave(leaveForm: VisitorForm?, evidence: VisitorEvidence): Single<VisitorEvidence> {
        evidence.token ?: return Single.error(Throwable("No TOKEN is available in this Evidence"))
        return sendRequest(networkRepo.leave(evidence.token!!, leaveForm)).flatMap { nEvidence ->
            // Get Local Visitor Activity by this Evidence
            localRepo.getActivity(evidence._id).map { return@map Pair(it, nEvidence) }
        }.flatMap { pair ->
            //Save Update Visitor Acitivity on Local Storage.
            val activity = pair.first
            val jsonObject = GsonHelper.getGson().toJsonTree(pair.second).asJsonObject
            activity.data = jsonObject
            activity.isActive = false
            activity.status = VisitorStatus.OUT
            localRepo.saveActivity(activity).andThen(Single.just(pair.second))
        }
    }

    override fun getProfile(id: String): Single<VisitorProfile> = localRepo.getProfile(id)

    override fun getProfileAsFlowable(id: String): Flowable<VisitorProfile> = localRepo.getProfileAsFlowable(id)

    override fun getActivitiesByProfileId(profileId: String, isActive: Boolean?): Flowable<List<VisitorActivityDB>> = localRepo.getActivitiesByProfileId(profileId, isActive)

    override fun getFullEvidenceById(evidenceId: String): Single<VisitorEvidence> {
        return localRepo.getActivity(evidenceId).flatMap { act ->
            val evidence = act.toVisitorEvidence()
            return@flatMap localRepo.getSite(act.siteId).map {
                if (evidence.site._id == it._id) {
                    evidence.site = it
                }
                return@map evidence
            }
        }
    }

    override fun saveProfile(profile: VisitorProfile): Completable = localRepo.saveProfile(profile)

    override fun loadSite(id: String): Single<VisitorSite> = localRepo.getSite(id)

    override fun setAutoSignOut(evidenceId: String, isAutoSignOut: Boolean): Completable {
        var nEvidence: VisitorEvidence? = null
        return localRepo.getActivity(evidenceId).flatMapCompletable { item ->
            nEvidence = item.toVisitorEvidence().apply {
                isAutoSignOutActive = isAutoSignOut
                generateNotificationId()
            }
            var nActivityDB = item.apply { this.data = GsonHelper.getGson().toJsonTree(nEvidence!!).asJsonObject }
            localRepo.saveActivity(nActivityDB)
        }
    }

//    fun setAutoSignOut(cd: CompositeDisposable, evidenceId: String, isAutoSignOut: Boolean) {
//        cd.add(setAutoSignOut(evidenceId, isAutoSignOut)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe())
//    }

}






