package au.com.safetychampion.data.visitor.data.local

import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorSiteEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorProfileEntity
import kotlinx.coroutines.flow.Flow

internal interface IVisitorLocalRepository {
    // Delete  ---------------------------------------------------------------------
    suspend fun deleteActivity(activity: VisitorActivityEntity)
    suspend fun deleteAllSites()
    suspend fun deleteProfile(profile: VisitorProfileEntity)

    // Get  ---------------------------------------------------------------------
    suspend fun getActivityById(activityId: String): VisitorActivityEntity?
    suspend fun getActivities(idList: List<String>): List<VisitorActivityEntity>?
    suspend fun getActivitiesByProfileId(profileId: String): Flow<List<VisitorActivityEntity>>?
    suspend fun getActivitiesByProfileId(profileId: String, isActive: Boolean): Flow<List<VisitorActivityEntity>>?
    suspend fun getProfileById(profileId: String): VisitorProfileEntity?
    suspend fun getProfileByIdFlowable(profileId: String): Flow<VisitorProfileEntity>?
    suspend fun getSiteById(siteId: String): VisitorSiteEntity?
    suspend fun getSitesByProfileId(profileId: String): Flow<List<VisitorSiteEntity>>?

    // Insert  ---------------------------------------------------------------------
    suspend fun insertActivity(vararg activity: VisitorActivityEntity)
    suspend fun insertProfile(profile: VisitorProfileEntity)
    suspend fun insertSites(vararg sites: VisitorSiteEntity)

    // Update  ---------------------------------------------------------------------
    suspend fun updateActivity(activity: VisitorActivityEntity)
    suspend fun updateProfile(profile: VisitorProfileEntity)
    suspend fun updateSite(site: VisitorSiteEntity)
}
