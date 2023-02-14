package au.com.safetychampion.data.visitor.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.data.visitor.domain.models.VisitorStatus
import com.google.gson.JsonObject
import java.util.*

@Entity(tableName = "VisitorActivities")
data class VisitorActivityEntity(
    @PrimaryKey val _id: String,
    val arriveDate: Date,
    var data: JsonObject,
    val profileId: String,
    var isActive: Boolean = false,
    var status: VisitorStatus,
    val siteId: String,
    val siteTitle: String,
    val siteDescription: String?,
    var token: String
) {
    internal suspend fun toVisitorEvidence(mapper: VisitorEntityMapper): VisitorEvidence? {
        TODO()
    }
}
