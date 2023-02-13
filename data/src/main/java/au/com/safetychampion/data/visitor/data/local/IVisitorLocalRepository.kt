package au.com.safetychampion.data.visitor.data.local

import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorProfile
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import kotlinx.coroutines.flow.Flow

internal interface IVisitorLocalRepository {
    suspend fun saveFetchSite(visitorSite: VisitorSite)

    suspend fun saveProfile(profile: VisitorProfile)

    suspend fun saveActivity(activityEntity: VisitorActivityEntity)

    suspend fun saveActivities(list: List<VisitorActivityEntity>)

    fun getActivitiesByProfileId(profileId: String, isActive: Boolean? = null): Flow<List<VisitorActivityEntity>>

    suspend fun getProfile(id: String): VisitorProfile?

    suspend fun getProfileAsFlow(id: String): Flow<VisitorProfile>

    suspend fun getSite(id: String): VisitorSite?

    suspend fun getActivityEntity(id: String): VisitorActivityEntity?

    suspend fun getActivitiesEntity(idList: List<String>): List<VisitorActivityEntity>?
}
