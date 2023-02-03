package au.com.safetychampion.scmobile.database.visitor

import au.com.safetychampion.scmobile.database.SCMDatabase
import au.com.safetychampion.scmobile.modules.global.MyGlobals
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Minh Khoi MAI on 24/11/20.
 */
class VisitorDBRepo: VisitorDAO {
    private val dao: VisitorDAO

    init {
        val db = SCMDatabase.getInstance(MyGlobals.getInstance().appInstance)
        dao = db.visitorDAO()
    }

    override fun getProfileById(profileId: String): Single<VisitorProfileDB> = dao.getProfileById(profileId)

    override fun getProfileByIdFlowable(profileId: String): Flowable<VisitorProfileDB> = dao.getProfileByIdFlowable(profileId)

    override fun insertProfile(profile: VisitorProfileDB): Completable = dao.insertProfile(profile)

    override fun updateProfile(profile: VisitorProfileDB): Completable = dao.updateProfile(profile)

    override fun deleteProfile(profile: VisitorProfileDB): Completable = dao.deleteProfile(profile)

    override fun getSitesByProfileId(profileId: String): Flowable<List<VisitorSiteDB>> = dao.getSitesByProfileId(profileId)

    override fun getSiteById(siteId: String): Single<VisitorSiteDB> = dao.getSiteById(siteId)

    override fun deleteAllSites(): Completable = dao.deleteAllSites()

    override fun updateSite(site: VisitorSiteDB): Completable = dao.updateSite(site)

    override fun insertSites(vararg sites: VisitorSiteDB): Completable = dao.insertSites(*sites)

    override fun getActivitiesByProfileId(profileId: String): Flowable<List<VisitorActivityDB>> = dao.getActivitiesByProfileId(profileId)

    override fun getActivitiesByProfileId(profileId: String, isActive: Boolean): Flowable<List<VisitorActivityDB>> = dao.getActivitiesByProfileId(profileId, isActive)

    override fun getActivityById(activityId: String): Single<VisitorActivityDB> = dao.getActivityById(activityId)

    override fun getActivities(idList: List<String>): Single<List<VisitorActivityDB>> = dao.getActivities(idList)

    override fun insertActivity(vararg activity: VisitorActivityDB): Completable = dao.insertActivity(*activity)

    override fun updateActivity(activity: VisitorActivityDB): Completable = dao.updateActivity(activity)

    override fun deleteActivity(activity: VisitorActivityDB): Completable = dao.deleteActivity(activity)
}