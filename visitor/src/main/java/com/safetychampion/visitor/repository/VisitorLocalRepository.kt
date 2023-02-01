package au.com.safetychampion.scmobile.visitorModule.repository

import au.com.safetychampion.scmobile.database.visitor.VisitorActivityDB
import au.com.safetychampion.scmobile.database.visitor.VisitorDBRepo
import au.com.safetychampion.scmobile.database.visitor.VisitorProfileDB
import au.com.safetychampion.scmobile.database.visitor.VisitorSiteDB
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMockData
import au.com.safetychampion.scmobile.visitorModule.models.VisitorProfile
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */

interface IVisitorLocalRepository {
    fun saveFetchSite(visitorSite: VisitorSite): Completable

    fun saveProfile(profile: VisitorProfile): Completable

    fun saveActivity(activityDB: VisitorActivityDB): Completable

    fun saveActivities(list: List<VisitorActivityDB>): Completable

    fun getActivitiesByProfileId(profileId: String, isActive: Boolean? = null): Flowable<List<VisitorActivityDB>>

    fun getProfile(id: String): Single<VisitorProfile>

    fun getProfileAsFlowable(id: String): Flowable<VisitorProfile>

    fun getSite(id: String): Single<VisitorSite>

    fun getActivity(id: String): Single<VisitorActivityDB>

    fun getActivities(ids: List<String>): Single<List<VisitorActivityDB>>
}

/**
 * Visitor LOCAL Repository
 * Often handle Local DB calls only.
 */
class VisitorLocalRepository: IVisitorLocalRepository {
    val db = VisitorDBRepo()

    override fun saveFetchSite(visitorSite: VisitorSite) = db.insertSites(visitorSite.toVisitorSiteDB(VisitorMockData.mockProfileId))
    override fun saveProfile(profile: VisitorProfile) = db.insertProfile(profile.toProfileDB())
    override fun saveActivity(activityDB: VisitorActivityDB) = db.insertActivity(activityDB)
    override fun saveActivities(list: List<VisitorActivityDB>): Completable = db.insertActivity(*list.toTypedArray())

    override fun getActivitiesByProfileId(profileId: String, isActive: Boolean?) = if (isActive == null) db.getActivitiesByProfileId(profileId) else db.getActivitiesByProfileId(profileId, isActive)
    override fun getProfile(id: String) = db.getProfileById(id).map(VisitorProfileDB::toVisitorProfile)
    override fun getProfileAsFlowable(id: String) = db.getProfileByIdFlowable(id).map(VisitorProfileDB::toVisitorProfile)
    override fun getSite(id: String) = db.getSiteById(id).map(VisitorSiteDB::toVisitorSite)
    override fun getActivity(id: String): Single<VisitorActivityDB> = db.getActivityById(id)
    override fun getActivities(ids: List<String>): Single<List<VisitorActivityDB>> = db.getActivities(ids)

}