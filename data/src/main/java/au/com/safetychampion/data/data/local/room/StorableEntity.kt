package au.com.safetychampion.data.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import au.com.safetychampion.data.domain.core.APIResponse
import com.google.gson.JsonObject
import java.util.Date

@Entity(tableName = "Storable")
internal data class StorableEntity(
    @PrimaryKey
    override val id: String,
    val data: JsonObject,
    override val createdAt: Date = Date()
) : BaseEntity {

    fun toAPIResponse() = APIResponse(true, data, null)
}

interface BaseEntity {
    val id: String
    val createdAt: Date
}
