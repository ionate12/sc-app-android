package au.com.safetychampion.scmobile.visitorModule.repository

import au.com.safetychampion.scmobile.database.visitor.VisitorActivityDB
import au.com.safetychampion.scmobile.database.visitor.VisitorDBRepo
import au.com.safetychampion.scmobile.database.visitor.VisitorProfileDB
import au.com.safetychampion.scmobile.database.visitor.VisitorSiteDB
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMockData
import au.com.safetychampion.scmobile.visitorModule.models.VisitorProfile
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import kotlinx.coroutines.flow.Flow

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */

interface IVisitorLocalRepository {
    suspend fun saveFetchSite(visitorSite: VisitorSite)

    suspend fun saveProfile(profile: VisitorProfile)

    suspend fun saveActivity(activityDB: VisitorActivityDB)

    suspend fun saveActivities(list: List<VisitorActivityDB>)

    fun getActivitiesByProfileId(profileId: String, isActive: Boolean? = null): Flow<List<VisitorActivityDB>>

    suspend fun getProfile(id: String): VisitorProfile

    fun getProfileAsFlowable(id: String): Flow<VisitorProfile>

    suspend fun getSite(id: String): VisitorSite

    suspend fun getActivity(id: String): VisitorActivityDB

    suspend fun getActivities(ids: List<String>): List<VisitorActivityDB>
}

/**
 * Visitor LOCAL Repository
 * Often handle Local DB calls only.
 */
class VisitorLocalRepository: IVisitorLocalRepository {
    val db = VisitorDBRepo()

    override suspend fun saveFetchSite(visitorSite: VisitorSite) = db.insertSites(visitorSite.toVisitorSiteDB(VisitorMockData.mockProfileId))
    override suspend fun saveProfile(profile: VisitorProfile) = db.insertProfile(profile.toProfileDB())
    override suspend fun saveActivity(activityDB: VisitorActivityDB) = db.insertActivity(activityDB)
    override suspend fun saveActivities(list: List<VisitorActivityDB>) =  db.insertActivity(*list.toTypedArray())

    override fun getActivitiesByProfileId(profileId: String, isActive: Boolean?) = if (isActive == null) db.getActivitiesByProfileId(profileId) else db.getActivitiesByProfileId(profileId, isActive)
    override suspend fun getProfile(id: String) = db.getProfileById(id).map(VisitorProfileDB::toVisitorProfile)
    override fun getProfileAsFlowable(id: String) = db.getProfileByIdFlowable(id).map(VisitorProfileDB::toVisitorProfile)
    override suspend fun getSite(id: String) = db.getSiteById(id).map(VisitorSiteDB::toVisitorSite)
    override suspend fun getActivity(id: String) = db.getActivityById(id)
    override suspend fun getActivities(ids: List<String>) = db.getActivities(ids)

}