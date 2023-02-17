package au.com.safetychampion.data.data.banner

import au.com.safetychampion.data.domain.BannerListItem
import au.com.safetychampion.data.domain.core.Result

interface IBannerRepository {
    suspend fun getListBanner(): Result<List<BannerListItem>>
}
