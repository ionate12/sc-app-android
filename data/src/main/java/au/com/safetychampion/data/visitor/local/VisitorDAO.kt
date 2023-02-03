package au.com.safetychampion.scmobile.database.visitor

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by Minh Khoi MAI on 23/11/20.
 */
@Dao
interface VisitorDAO {
    //region PROFILE
    @Query("SELECT * FROM VisitorProfiles WHERE _id=:profileId")
    suspend fun getProfileById(profileId: String): VisitorProfileDB

    @Query("SELECT * FROM VisitorProfiles WHERE _id=:profileId")
    fun getProfileByIdFlowable(profileId: String): Flow<VisitorProfileDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: VisitorProfileDB)

    @Update
    suspend fun updateProfile(profile: VisitorProfileDB)

    @Delete
    suspend fun deleteProfile(profile: VisitorProfileDB)
    //endregion

    //region SITE

    @Query("SELECT * FROM VisitorSites WHERE profileId=:profileId")
    fun getSitesByProfileId(profileId: String): Flow<List<VisitorSiteDB>>

    @Query("SELECT * FROM VisitorSites WHERE _id=:siteId")
    suspend fun getSiteById(siteId: String): VisitorSiteDB

    @Query("DELETE FROM VisitorSites")
    suspend fun deleteAllSites()

    @Update
    suspend fun updateSite(site : VisitorSiteDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSites(vararg sites: VisitorSiteDB)

    //endregion

    //region ACTIVITIES

    @Query("SELECT * FROM VisitorActivities WHERE profileId=:profileId")
    fun getActivitiesByProfileId(profileId: String): Flow<List<VisitorActivityDB>>

    @Query("SELECT * FROM VisitorActivities WHERE _id=:activityId")
    suspend fun getActivityById(activityId: String): VisitorActivityDB

    @Query("SELECT * FROM VisitorActivities WHERE _id in (:idList)")
    suspend fun getActivities(idList: List<String>): List<VisitorActivityDB>

    @Query("SELECT * FROM VisitorActivities WHERE profileId=:profileId AND isActive=:isActive")
    suspend fun getActivitiesByProfileId(profileId: String, isActive: Boolean): List<VisitorActivityDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(vararg activity: VisitorActivityDB)

    @Update
    suspend fun updateActivity(activity: VisitorActivityDB)

    @Delete
    suspend fun deleteActivity(activity: VisitorActivityDB)
}