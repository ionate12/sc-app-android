package au.com.safetychampion.data.data.mobileadmin

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.VersionBoard

interface IMobileAdminRepository {
    suspend fun getVersion():Result<VersionBoard>
    suspend fun getAnnouncement():Result<>
}