package au.com.safetychampion.data.domain.usecase.noticeboard

import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardFormPL

class SubmitNoticeboardFormUseCase(
    private val repository: INoticeboardRepository
) {
    suspend fun invoke(payload: NoticeboardFormPL) = repository.submitForm(payload)
}
