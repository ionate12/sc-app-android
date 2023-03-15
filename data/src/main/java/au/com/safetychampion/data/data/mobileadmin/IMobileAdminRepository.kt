package au.com.safetychampion.data.data.mobileadmin

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.MobileAdmin
import au.com.safetychampion.data.domain.models.VersionBoard
import au.com.safetychampion.data.domain.usecase.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.usecase.mobileadmin.VersionPL

interface IMobileAdminRepository {
    suspend fun getVersion(payload: VersionPL): Result<VersionBoard>
    suspend fun getAnnouncement(payload: AnnouncementPL): Result<List<MobileAdmin>>
}
