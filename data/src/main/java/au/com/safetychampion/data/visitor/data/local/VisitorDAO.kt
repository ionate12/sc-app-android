package au.com.safetychampion.data.visitor.data.local

import androidx.room.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorSiteEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface VisitorDAO {
    // Delete  ---------------------------------------------------------------------
    @Delete
    suspend fun deleteActivity(activity: VisitorActivityEntity)

    @Query("DELETE FROM VisitorSites")
    suspend fun deleteAllSites()

    @Delete
    suspend fun deleteProfile(profile: VisitorProfileEntity)

    // Get  ---------------------------------------------------------------------
    @Query("SELECT * FROM VisitorActivities WHERE _id=:activityId")
    suspend fun getActivityById(activityId: String): VisitorActivityEntity?

    @Query("SELECT * FROM VisitorActivities WHERE _id in (:idList)")
    suspend fun getActivities(idList: List<String>): List<VisitorActivityEntity>?

    @Query("SELECT * FROM VisitorActivities WHERE profileId=:profileId")
    fun getActivitiesByProfileId(profileId: String): Flow<List<VisitorActivityEntity>>

    @Query("SELECT * FROM VisitorActivities WHERE profileId=:profileId AND isActive=:isActive")
    fun getActivitiesByProfileId(
        profileId: String,
        isActive: Boolean
    ): Flow<List<VisitorActivityEntity>>

    @Query("SELECT * FROM VisitorProfiles WHERE _id=:profileId")
    suspend fun getProfileById(profileId: String): VisitorProfileEntity?

    @Query("SELECT * FROM VisitorProfiles WHERE _id=:profileId")
    fun getProfileByIdFlowable(profileId: String): Flow<VisitorProfileEntity>

    @Query("SELECT * FROM VisitorSites WHERE _id=:siteId")
    suspend fun getSiteEntityById(siteId: String): VisitorSiteEntity?

    @Query("SELECT * FROM VisitorSites WHERE profileId=:profileId")
    fun getSitesByProfileId(profileId: String): Flow<List<VisitorSiteEntity>>?

    // Insert ---------------------------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(vararg activity: VisitorActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: VisitorProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSites(vararg sites: VisitorSiteEntity)

    // Update ---------------------------------------------------------------------
    @Update
    suspend fun updateActivity(activity: VisitorActivityEntity)

    @Update
    suspend fun updateProfile(profile: VisitorProfileEntity)

    @Update
    suspend fun updateSite(site: VisitorSiteEntity)
}
