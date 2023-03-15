package au.com.safetychampion.data.domain.models.pushnotification
import java.util.*

data class PushNotificationViewItem(
    val _id: String,
    val title: String,
    val body: String,
    val env: PushNotificationEnv,
    val dateCreated: Date,
    val isRead: Boolean = false
)
