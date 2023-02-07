package au.com.safetychampion.data.visitor.data.local

import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorSiteEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorProfileEntity
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.flow.Flow

internal class VisitorLocalRepositoryImpl : IVisitorLocalRepository {
    private val dao: VisitorDAO by koinInject()

    // Delete  ---------------------------------------------------------------------
    override suspend fun deleteActivity(activity: VisitorActivityEntity) {
        return dao.deleteActivity(activity)
    }

    override suspend fun deleteAllSites() {
        return dao.deleteAllSites()
    }

    override suspend fun deleteProfile(profile: VisitorProfileEntity) {
        return dao.deleteProfile(profile)
    }

    // Get  ---------------------------------------------------------------------
    override suspend fun getActivityById(activityId: String): VisitorActivityEntity? {
        return dao.getActivityById(activityId)
    }

    override suspend fun getActivities(idList: List<String>): List<VisitorActivityEntity>? {
        return dao.getActivities(idList)
    }

    override suspend fun getActivitiesByProfileId(profileId: String): Flow<List<VisitorActivityEntity>>? {
        return dao.getActivitiesByProfileId(profileId)
    }

    override suspend fun getActivitiesByProfileId(
        profileId: String,
        isActive: Boolean
    ): Flow<List<VisitorActivityEntity>>? {
        return dao.getActivitiesByProfileId(profileId, isActive)
    }

    override suspend fun getProfileById(profileId: String): VisitorProfileEntity? {
        return dao.getProfileById(profileId)
    }

    override suspend fun getProfileByIdFlowable(profileId: String): Flow<VisitorProfileEntity>? {
        return dao.getProfileByIdFlowable(profileId)
    }

    override suspend fun getSiteById(siteId: String): VisitorSiteEntity? {
        return dao.getSiteById(siteId)
    }

    override suspend fun getSitesByProfileId(profileId: String): Flow<List<VisitorSiteEntity>>? {
        return dao.getSitesByProfileId(profileId)
    }

    // Insert ---------------------------------------------------------------------
    override suspend fun insertActivity(vararg activity: VisitorActivityEntity) {
        return dao.insertActivity(*activity)
    }

    override suspend fun insertProfile(profile: VisitorProfileEntity) {
        return dao.insertProfile(profile)
    }

    override suspend fun insertSites(vararg sites: VisitorSiteEntity) {
        return dao.insertSites(*sites)
    }

    // Update  ---------------------------------------------------------------------
    override suspend fun updateActivity(activity: VisitorActivityEntity) {
        return dao.updateActivity(activity)
    }

    override suspend fun updateProfile(profile: VisitorProfileEntity) {
        return dao.updateProfile(profile)
    }

    override suspend fun updateSite(site: VisitorSiteEntity) {
        return dao.updateSite(site)
    }
}
