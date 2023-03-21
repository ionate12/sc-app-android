package au.com.safetychampion.data.data.mobileadmin

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.MobileAdminAPI
import au.com.safetychampion.data.data.local.BaseAppDataStore
import au.com.safetychampion.data.data.local.StoreKey
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.models.mobileadmin.MobileAdmin
import au.com.safetychampion.data.domain.models.mobileadmin.VersionBoard
import au.com.safetychampion.data.domain.usecase.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.usecase.mobileadmin.VersionPL
import au.com.safetychampion.data.util.extension.koinInject

class MobileAdminRepository : BaseRepository(), IMobileAdminRepository {
    private val dataStore: BaseAppDataStore by koinInject()

    override suspend fun getVersion(payload: VersionPL): Result<VersionBoard> {
        return MobileAdminAPI.Version(
            payload
        )
            .call<VersionBoard>()
            .doOnSuccess { dataStore.store(StoreKey.VersionBoard, it) }
            .flatMapError {
                var versionBoard: VersionBoard? = null
                if (it is SCError.NoNetwork && dataStore.get<VersionBoard>(StoreKey.VersionBoard).also { versionBoard = it } != null) {
                    Result.Success(versionBoard!!)
                } else {
                    Result.Error(it)
                }
            }
    }

    override suspend fun getAnnouncement(payload: AnnouncementPL): Result<List<MobileAdmin>> {
        return MobileAdminAPI.Announcement(payload).call()
    }
}
