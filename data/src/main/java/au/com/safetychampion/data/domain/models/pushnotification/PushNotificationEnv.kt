package au.com.safetychampion.data.domain.models.pushnotification

import au.com.safetychampion.data.domain.models.auth.LoginUser
import au.com.safetychampion.data.domain.models.task.Task
import java.io.Serializable

// /NO USE
data class PushNotificationEnv(
    val itemId: String,
    val notifId: Int,
    val dueType: String?,
    val user: LoginUser?,
    val task: Task?
) : Serializable
