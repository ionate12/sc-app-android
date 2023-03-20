package au.com.safetychampion.data.visitor.domain.usecase.site

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.visitor.domain.models.VisitorPayload
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import au.com.safetychampion.data.visitor.domain.usecase.BaseVisitorUseCase

class FetchSiteUseCase : BaseVisitorUseCase() {
    suspend operator fun invoke(payload: VisitorPayload.SiteFetch): Result<VisitorSite> {
        return remoteRepository.siteFetch(payload)
            .doOnSuccess {
                localRepository.saveFetchSite(it)
            }
    }
}
