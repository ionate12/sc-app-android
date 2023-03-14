package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.models.pushnotification.FirebaseTokenPL

interface PushNotificationAPI {
    class Register(val payload: FirebaseTokenPL) : NetworkAPI.Post(
        path = "users/notifications/devicetoken/new",
        body = payload
    )

    class Fetch(val payload: FirebaseTokenPL) : NetworkAPI.Post(
        path = "users/notifications/devicetoken/fetch",
        body = payload
    )

    class Delete(val payload: FirebaseTokenPL) : NetworkAPI.Post(
        path = "users/notifications/devicetoken/delete",
        body = payload
    )
}
