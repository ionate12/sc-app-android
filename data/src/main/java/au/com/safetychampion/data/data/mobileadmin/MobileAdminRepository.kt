package au.com.safetychampion.data.data.mobileadmin

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.MobileAdminAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.VersionBoard
import au.com.safetychampion.data.domain.models.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.models.mobileadmin.MobileAdmin
import au.com.safetychampion.data.domain.models.mobileadmin.VersionPL

class MobileAdminRepository : BaseRepository(), IMobileAdminRepository {
    override suspend fun getVersion(payload: VersionPL): Result<VersionBoard> {
        return MobileAdminAPI.GetVersion(payload).call()
    }

    override suspend fun getAnnouncement(payload: AnnouncementPL): Result<MobileAdmin> {
        return MobileAdminAPI.GetAnnouncement(payload).call()
    }
}
