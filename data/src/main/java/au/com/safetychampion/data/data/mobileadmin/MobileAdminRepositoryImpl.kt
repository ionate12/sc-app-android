package au.com.safetychampion.data.data.mobileadmin

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.MobileAdminAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.VersionBoard

class MobileAdminRepositoryImpl: BaseRepository(), IMobileAdminRepository {
    override suspend fun getVersion(): Result<VersionBoard> {
        return MobileAdminAPI.GetVersion().call()
    }

}