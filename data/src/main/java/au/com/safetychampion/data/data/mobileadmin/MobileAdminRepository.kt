package au.com.safetychampion.data.data.mobileadmin

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.MobileAdminAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.models.MobileAdmin
import au.com.safetychampion.data.domain.models.VersionBoard
import au.com.safetychampion.data.domain.usecase.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.usecase.mobileadmin.VersionPL

class MobileAdminRepository : BaseRepository(), IMobileAdminRepository {
    override suspend fun getVersion(payload: VersionPL): Result<VersionBoard> {
        return MobileAdminAPI.Version(
            payload
        )
            .call<VersionBoard>()
            .doOnSuccess {
//                TODO("save to sharedpref")
            }
            .flatMapError {
                if (it is SCError.NoNetwork) {
                    TODO("get From sharedpref")
                } else {
                    Result.Error(it)
                }
            }
    }

    override suspend fun getAnnouncement(payload: AnnouncementPL): Result<List<MobileAdmin>> {
        return MobileAdminAPI.Announcement(payload).call()
    }
}
