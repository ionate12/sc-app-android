package au.com.safetychampion.data.data.banner

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.BannerListItem
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.util.koinInject

class BannerRepositoryImpl : BaseRepository(), IBannerRepository {
    private val api: BannerAPI by koinInject()

    override suspend fun getListBanner(): Result<List<BannerListItem>> {
        return apiCallAsList { api.list() }
    }
}
