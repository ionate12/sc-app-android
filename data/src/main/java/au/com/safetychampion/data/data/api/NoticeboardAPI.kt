package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardFormPL

interface NoticeboardAPI {
    class List() : NetworkAPI.Post(
        path = "noticeboard/boards/list",
        body = BasePL.empty()
    )

    class BlockList(noticeboardId: String) : NetworkAPI.Get(
        path = "noticeboard/boards/$noticeboardId/blocklist/fetch"
    )

    class Vdoc(noticeboardId: String) : NetworkAPI.Get(
        path = "noticeboard/boards/$noticeboardId/blocklist/vdocs"
    )

    class Fetch(noticeboardId: String) : NetworkAPI.Get(
        path = "noticeboard/forms/$noticeboardId/fetch"
    )

    class SubmitForm(payload: NoticeboardFormPL) : NetworkAPI.Post(
        path = "noticeboard/formsubmission",
        body = payload
    )
}
