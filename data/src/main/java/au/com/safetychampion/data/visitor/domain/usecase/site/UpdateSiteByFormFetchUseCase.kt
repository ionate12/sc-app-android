package au.com.safetychampion.data.visitor.domain.usecase.site // ktlint-disable filename

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.visitor.domain.models.VisitorPayload
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import au.com.safetychampion.data.visitor.domain.usecase.BaseVisitorUseCase

class UpdateSiteByFormFetchUseCase : BaseVisitorUseCase() {
    /**
     * Used in VisitorSignOutFragment, VisitorPhoneInputFragment for form initialization;
     * Renamed from fetchArriveForm() */

    suspend operator fun invoke(
        payload: VisitorPayload.FormFetch,
        site: VisitorSite
    ): Result<VisitorSite> {
        // 1. Fetch form
        return remoteRepository.formFetch(payload)
            .flatMap { nForm ->
                // and then update site with fetched form
                for (parentForm in site.forms) {
                    if (parentForm.arrive?.form?._id == nForm._id) {
                        parentForm.arrive.form = nForm
                    }

                    if (parentForm.leave?.form?._id == nForm._id) {
                        parentForm.leave.form = nForm
                    }
                }
                // also save sites to database
                localRepository.saveFetchSite(site)
                // return updated site
                Result.Success(site)
            }
    }
}
