package au.com.safetychampion.data.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import java.util.Date

@Entity(tableName = "Syncable")
internal data class SyncableEntity(
    @PrimaryKey
    override val id: String,
    val data: JsonObject,
    val status: Status,
    override val createdAt: Date = Date()
) : BaseEntity {
    enum class Status {
        PENDING, SYNCING, EDITING
    }

    // TODO: Add more specs when use this to show as offline tasks.
}
