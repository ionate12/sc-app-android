package au.com.safetychampion.data.domain.usecase.mobileadmin

import au.com.safetychampion.data.data.mobileadmin.IMobileAdminRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.models.mobileadmin.MobileAdmin
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetAnnouncementUseCase : BaseUseCase() {
    private val repository: IMobileAdminRepository by koinInject()

    suspend operator fun invoke(
        payload: AnnouncementPL
    ): Result<MobileAdmin> {
        return repository.getAnnouncement(payload)
    }
}
