package au.com.safetychampion.data.domain.usecase.mobileadmin

import au.com.safetychampion.data.data.mobileadmin.IMobileAdminRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.errorOf
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.models.MobileAdmin
import timber.log.Timber

class AnnouncementPL(
    val filter: VersionPL
) : BasePL()

class GetAnnouncementUseCase(
    private val repository: IMobileAdminRepository
) {
    suspend fun invoke(): Result<MobileAdmin> {
        return repository.getAnnouncement(
            payload = AnnouncementPL(VersionPL())
        )
            .flatMap {
                it.firstOrNull { mbAdmins -> mbAdmins.category == "SIMPLE_ANNOUNCEMENT" }
                    ?.let { mbAdmin ->
                        // TODO("overwriteSimpleAnnouncement") }
                        Timber.tag("overwriteSimpleAnnouncement").d("overwritten")
                        Result.Success(mbAdmin)
                    } ?: errorOf("No Simple Announcement found")
            }
    }
}
