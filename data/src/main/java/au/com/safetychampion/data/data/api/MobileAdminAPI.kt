package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.models.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.models.mobileadmin.VersionPL

interface MobileAdminAPI {
    class GetVersion(
        body: VersionPL
    ) : NetworkAPI.Post(
        path = "/mobileappadmin/versions/fetch",
        body = body
    )

    class GetAnnouncement(
        body: AnnouncementPL
    ) : NetworkAPI.Post(
        path = "/mobileappadmin/announcements/list/active",
        body = body
    )
}
