package au.com.safetychampion.data.data.noticeboard

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.NoticeboardAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.models.document.Document
import au.com.safetychampion.data.domain.models.noticeboard.Noticeboard
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardFormPL
import au.com.safetychampion.data.visitor.domain.models.VisitorForm
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class NoticeboardRepository : BaseRepository(), INoticeboardRepository {
    override suspend fun list(): Result<List<Noticeboard>> {
        return NoticeboardAPI.List().call()
    }

    override suspend fun blockList(noticeboardId: String): Result<List<NoticeboardBlockList>> {
        return NoticeboardAPI.BlockList(noticeboardId).call()
    }

    override suspend fun vdoc(noticeboardID: String): Result<List<Document>> {
        return NoticeboardAPI.Vdoc(noticeboardID).call()
    }

    override suspend fun fetch(noticeboardID: String): Result<VisitorForm> {
        return NoticeboardAPI.Fetch(noticeboardID).call()
    }

    override suspend fun fetchMultiple(idList: List<String>): Result.Success<List<VisitorForm>> {
        return Result.Success(
            withContext(dispatchers.io) {
                idList
                    .map { async { fetch(it).dataOrNull() } }
                    .awaitAll()
                    .filterNotNull()
            }
        )
    }

    override suspend fun submitForm(payload: NoticeboardFormPL): Result<Boolean> {
        return NoticeboardAPI.SubmitForm(payload).call()
    }
}
