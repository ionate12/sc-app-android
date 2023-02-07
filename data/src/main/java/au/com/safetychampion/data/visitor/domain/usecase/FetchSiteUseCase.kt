package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import au.com.safetychampion.data.visitor.domain.usecase.internal.BaseVisitorUseCase

class FetchSiteUseCase : BaseVisitorUseCase() {
    private val mapper by lazy { VisitorEntityMapper() }

    suspend operator fun invoke(token: String): Result<VisitorSite> {
        return remoteRepository.siteFetch(token)
            .doOnSucceed {
                localRepository.insertSites(
                    it.toVisitorSiteEntity(mapper, "profileId") // TODO("profileId")
                )
            }
    }
}
