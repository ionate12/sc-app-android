package au.com.safetychampion.data.visitor.data.local

import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.data.visitor.domain.models.VisitorMockData
import au.com.safetychampion.data.visitor.domain.models.VisitorProfile
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

internal class VisitorLocalRepositoryImpl : IVisitorLocalRepository {
    private val dao: VisitorDAO by koinInject()
    private val mapper by lazy { VisitorEntityMapper() }

    override suspend fun saveFetchSite(visitorSite: VisitorSite) {
        dao.insertSites(
            visitorSite.toVisitorSiteEntity(
                mapper = mapper,
                profileID = VisitorMockData.mockProfileId
            )
        )
    }
    override suspend fun saveProfile(profile: VisitorProfile) {
        dao.insertProfile(profile.toProfileEntity())
    }
    override suspend fun saveActivity(activityEntity: VisitorActivityEntity) {
        dao.insertActivity(activityEntity)
    }
    override suspend fun saveActivities(list: List<VisitorActivityEntity>) {
        dao.insertActivity(*list.toTypedArray())
    }
    override fun getActivitiesByProfileId(profileId: String, isActive: Boolean?): Flow<List<VisitorActivityEntity>> {
        return if (isActive == null) dao.getActivitiesByProfileId(profileId) else dao.getActivitiesByProfileId(profileId, isActive)
    }
    override suspend fun getProfile(id: String): VisitorProfile? {
        return dao.getProfileById(id)?.toVisitorProfile()
    }

    override suspend fun getProfileAsFlow(id: String): Flow<VisitorProfile> {
        return dao.getProfileByIdFlowable(id).transform { it.toVisitorProfile() }
    }
    override suspend fun getSite(id: String): VisitorSite? {
        return dao.getSiteEntityById(id)?.toVisitorSite(mapper)
    }
    override suspend fun getActivityEntity(id: String): VisitorActivityEntity? {
        return dao.getActivityById(id)
    }
    override suspend fun getActivitiesEntity(idList: List<String>): List<VisitorActivityEntity>? {
        return dao.getActivities(idList)
    }
}
