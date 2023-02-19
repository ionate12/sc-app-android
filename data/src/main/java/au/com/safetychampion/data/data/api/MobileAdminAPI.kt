package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.base.BasePL

interface MobileAdminAPI {
    class GetVersion(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "/mobileappadmin/versions/fetch",
        body = body
    )

    class GetAnnouncement(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "/mobileappadmin/announcements/list/active",
        body = body
    )
}
