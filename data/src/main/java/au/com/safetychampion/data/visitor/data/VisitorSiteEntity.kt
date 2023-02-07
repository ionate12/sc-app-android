package au.com.safetychampion.data.visitor.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import com.google.gson.JsonObject

@Entity(tableName = "VisitorSites")
internal data class VisitorSiteEntity(
    @PrimaryKey val _id: String,
    val profileId: String, // Foreign key of Profile
    val type: String,
    val title: String,
    val description: String?,
    val category: String?,
    val categoryOther: String?,
    val subcategory: String?,
    val subcategoryOther: String?,
    val data: JsonObject

) {
    suspend fun toVisitorSite(mapper: VisitorEntityMapper): VisitorSite? {
        return mapper.toVisitorSite(this)
    }
}
