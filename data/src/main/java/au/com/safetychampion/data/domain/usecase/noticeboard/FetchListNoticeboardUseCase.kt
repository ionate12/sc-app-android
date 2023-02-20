package au.com.safetychampion.data.domain.usecase.noticeboard

import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.domain.models.noticeboard.Noticeboard

class FetchListNoticeboardUseCase(
    private val repository: INoticeboardRepository
) {
    /**@param tier The current user tier */
    suspend fun invoke(tier: TierType): Result<List<Noticeboard>> {
        return repository.list().map { noticeboards ->
            if (tier == TierType.T3) {
                noticeboards.filter { it.allocationOf == null }
            } else {
                noticeboards
            }
        }
    }
}
