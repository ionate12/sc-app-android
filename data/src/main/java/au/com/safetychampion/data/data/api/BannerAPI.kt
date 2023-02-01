package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.BannerListPayload

interface BannerAPI {
    class List(
        body: BannerListPayload = BannerListPayload()
    ) : NetworkAPI.Post(
        path = "dashboardbanner/list",
        body = body
    )
}
