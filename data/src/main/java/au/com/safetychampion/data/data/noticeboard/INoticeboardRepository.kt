package au.com.safetychampion.data.data.noticeboard

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.Document
import au.com.safetychampion.data.domain.models.noticeboard.Noticeboard
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardFormPL
import au.com.safetychampion.data.visitor.domain.models.VisitorForm

interface INoticeboardRepository {
    suspend fun list(): Result<List<Noticeboard>>
    suspend fun blockList(noticeboardId: String): Result<List<NoticeboardBlockList>>
    suspend fun vdoc(noticeboardID: String): Result<List<Document>>
    suspend fun fetch(noticeboardID: String): Result<VisitorForm>
    suspend fun fetchMultiple(idList: List<String>): Result<List<VisitorForm>>
    suspend fun submitForm(payload: NoticeboardFormPL): Result<Boolean>
}
