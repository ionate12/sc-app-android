package au.com.safetychampion.data.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import au.com.safetychampion.data.domain.models.auth.LoginUser
import com.google.gson.JsonObject

@Entity(
    indices = [Index("userId")],
    tableName = "MasterTable",
    primaryKeys = ["_id", "type"],
    foreignKeys = [
        ForeignKey(
            entity = LoginUser::class,
            parentColumns = arrayOf("_id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MasterCell(
    var _id: String,
    var userId: String,
    var type: String,
    var createdDate: String? = null,
    var createdTime: String? = null,
    var state: Int = 0,
    var `object`: JsonObject? = null
)
