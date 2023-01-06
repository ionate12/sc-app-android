package au.com.safetychampion.data.domain.models.visitor

import androidx.room.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.uncategory.GsonHelper
import com.google.gson.JsonObject
import java.util.*

/**
 * Created by Minh Khoi MAI on 23/11/20.
 */

@Entity(tableName = "VisitorProfiles")
data class VisitorProfileDB(
    @PrimaryKey val _id: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val phoneCountryCode: String?
)

@Entity(tableName = "VisitorSites")
data class VisitorSiteDB(
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
    fun toVisitorSite(): VisitorSite {
        return GsonHelper.getGson().fromJson(data, VisitorSite::class.java)
    }
}

@Entity(tableName = "VisitorActivities")
data class VisitorActivityDB(
    @PrimaryKey val _id: String,
    val profileId: String,
    val siteId: String,
    val arriveDate: Date,
    var status: VisitorStatus,
    val siteTitle: String,
    val siteDescription: String?,
    var isActive: Boolean = false,
    var data: JsonObject,
    var token: String
)

// Relation Model
data class VisitorProfileAndSiteDB(
    @Embedded val profile: VisitorProfileDB,
    @Relation(
        parentColumn = "_id",
        entityColumn = "profileId"
    )
    val sites: List<VisitorSiteDB>
)

enum class VisitorStatus(val value: String) {
    IN("In"), OUT("Out")
}
