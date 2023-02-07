package au.com.safetychampion.data.visitor.data.local

import androidx.room.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorSiteEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface VisitorDAO : IVisitorLocalRepository {
    // Delete  ---------------------------------------------------------------------
    @Delete
    override suspend fun deleteActivity(activity: VisitorActivityEntity)

    @Query("DELETE FROM VisitorSites")
    override suspend fun deleteAllSites()

    @Delete
    override suspend fun deleteProfile(profile: VisitorProfileEntity)

    // Get  ---------------------------------------------------------------------
    @Query("SELECT * FROM VisitorActivities WHERE _id=:activityId")
    override suspend fun getActivityById(activityId: String): VisitorActivityEntity?

    @Query("SELECT * FROM VisitorActivities WHERE _id in (:idList)")
    override suspend fun getActivities(idList: List<String>): List<VisitorActivityEntity>?

    @Query("SELECT * FROM VisitorActivities WHERE profileId=:profileId")
    override suspend fun getActivitiesByProfileId(profileId: String): Flow<List<VisitorActivityEntity>>?

    @Query("SELECT * FROM VisitorActivities WHERE profileId=:profileId AND isActive=:isActive")
    override suspend fun getActivitiesByProfileId(
        profileId: String,
        isActive: Boolean
    ): Flow<List<VisitorActivityEntity>>?

    @Query("SELECT * FROM VisitorProfiles WHERE _id=:profileId")
    override suspend fun getProfileById(profileId: String): VisitorProfileEntity?

    @Query("SELECT * FROM VisitorProfiles WHERE _id=:profileId")
    override suspend fun getProfileByIdFlowable(profileId: String): Flow<VisitorProfileEntity>?

    @Query("SELECT * FROM VisitorSites WHERE _id=:siteId")
    override suspend fun getSiteById(siteId: String): VisitorSiteEntity?

    @Query("SELECT * FROM VisitorSites WHERE profileId=:profileId")
    override suspend fun getSitesByProfileId(profileId: String): Flow<List<VisitorSiteEntity>>?

    // Insert ---------------------------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertActivity(vararg activity: VisitorActivityEntity)

    override suspend fun insertProfile(profile: VisitorProfileEntity)

    override suspend fun insertSites(vararg sites: VisitorSiteEntity)

    // Update ---------------------------------------------------------------------
    override suspend fun updateActivity(activity: VisitorActivityEntity)

    override suspend fun updateProfile(profile: VisitorProfileEntity)

    override suspend fun updateSite(site: VisitorSiteEntity)
}
