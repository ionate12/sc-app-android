package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.safetychampion.scmobile.database.visitor.VisitorActivityDB
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMockData
import au.com.safetychampion.scmobile.visitorModule.models.VisitorProfile
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisitorDashboardViewModel : ViewModel() {
    val profile: MutableLiveData<VisitorProfile> = MutableLiveData()
    val recentActivities: MutableLiveData<List<VisitorActivityDB>> = MutableLiveData(listOf())
    val repo = VisitorRepository
    val cd = CompositeDisposable()
    val profileId = VisitorMockData.mockProfileId

    init {
        observeProfile()
        observeRecentActivities(profileId)
    }

    private fun observeProfile() {
        cd.add(repo.getProfileAsFlowable(profileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { p -> profile.postValue(p) })
    }

    private fun observeRecentActivities(id: String) {
        cd.add(repo.getActivitiesByProfileId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ activities ->
                    recentActivities.value = activities
                }, { throwable ->
                    throwable.printStackTrace()
                    recentActivities.value = listOf()
                }, {
                    //DO Nothing onComplete.
                })
        )
    }

    fun loadVisitorSite(id: String): Single<VisitorSite> {
        return repo.loadSite(id)
    }

    fun fetchVisitorSite(id:String):Single<VisitorSite>{
        return repo.getSiteFetch(id)
    }

    fun loadEvidence(evidenceId: String): Single<VisitorEvidence> {
        return repo.getFullEvidenceById(evidenceId)
    }

    /**
     * Fetch Visit fully from Server including site from DB
     */
    fun fetchVisitAndIncludeSiteFromDB(evidenceId: String): Single<VisitorEvidence> {
        return loadEvidence(evidenceId)
                .flatMap { localEvidence -> repo.getVisitFetch(listOf(localEvidence)).map { list -> list.find { it._id == evidenceId } } }
                .flatMap { evidence ->
                    return@flatMap repo.loadSite(evidence.site._id)
                            .map { site ->
                                evidence.apply { this.site = site }
                            }.onErrorResumeNext { Single.just(evidence) } }
    }

    fun fetchVisits(profileId: String): Single<List<VisitorEvidence>> {
        return repo.getActivitiesByProfileId(profileId, isActive = true)
                .take(1)
                .singleOrError()
                .map { list -> return@map list.map { it.toVisitorEvidence() } }
                .flatMap { if (it.isEmpty()) Single.just(listOf()) else repo.getVisitFetch(it) }
    }

    fun setAutoSignout(evidenceId: String): Completable {
        return repo.setAutoSignOut(evidenceId, true)
    }


}