package au.com.safetychampion.data.data.banner

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.BannerAPI
import au.com.safetychampion.data.domain.BannerListItem
import au.com.safetychampion.data.domain.core.Result

class BannerRepository : BaseRepository(), IBannerRepository {

    override suspend fun getListBanner(): Result<List<BannerListItem>> {
        return BannerAPI.List().call()
    }
}
