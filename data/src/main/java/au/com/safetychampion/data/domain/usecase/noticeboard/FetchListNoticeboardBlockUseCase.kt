package au.com.safetychampion.data.domain.usecase.noticeboard

import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository

class FetchListNoticeboardBlockUseCase(
    private val repository: INoticeboardRepository
) {
    suspend fun invoke(noticeboardId: String) = repository.blockList(noticeboardId)
}
