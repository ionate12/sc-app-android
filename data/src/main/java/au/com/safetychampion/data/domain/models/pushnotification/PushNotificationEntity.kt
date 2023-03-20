package au.com.safetychampion.data.domain.models.pushnotification

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import java.util.*

@Entity(tableName = "PushNotification")
data class PushNotificationEntity(
    @PrimaryKey
    val _id: String,
    val userId: String,
    val title: String?,
    val body: String?,
    val data: JsonObject?,
    var isRead: Boolean = false,
    val dateCreated: Date = Date()
)
