package au.com.safetychampion.data.domain.usecase.banner

import au.com.safetychampion.data.data.banner.IBannerRepository
import au.com.safetychampion.data.domain.BannerListItem
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.util.extension.koinInject

class GetListBannerUseCase {
    private val repository: IBannerRepository by koinInject()
    suspend operator fun invoke(): Result<List<BannerListItem>> {
        return repository.getListBanner()
    }
}
