package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.usecase.mobileadmin.AnnouncementPL
import au.com.safetychampion.data.domain.usecase.mobileadmin.VersionPL

interface MobileAdminAPI {
    class Version(payload: VersionPL) : NetworkAPI.Post(
        path = "/mobileappadmin/versions/fetch",
        body = payload
    )

    class Announcement(
        payload: AnnouncementPL
    ) : NetworkAPI.Post(
        path = "/mobileappadmin/announcements/list/active",
        body = payload
    )
}
