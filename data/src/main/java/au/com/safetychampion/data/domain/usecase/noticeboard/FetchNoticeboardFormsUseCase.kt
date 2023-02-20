package au.com.safetychampion.data.domain.usecase.noticeboard

import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.visitor.domain.models.VisitorForm

class FetchNoticeboardFormsUseCase(
    private val repository: INoticeboardRepository
) {
    suspend fun invoke(idList: List<String>): Result<List<VisitorForm>> {
        return repository.fetchMultiple(idList)
    }
}
