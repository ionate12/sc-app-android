package au.com.safetychampion.scmobile.database.visitor

import androidx.room.*
import au.com.safetychampion.scmobile.visitorModule.models.*
import com.google.gson.Gson
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
) {
    fun toVisitorProfile() = VisitorProfile().apply {
        this._id = this@VisitorProfileDB._id
        this.name.set(this@VisitorProfileDB.name)
        this.email.set(this@VisitorProfileDB.email)
        this.phoneNumber.set(this@VisitorProfileDB.phone)
        this.phoneCountryCode.set(this@VisitorProfileDB.phoneCountryCode)
    }
}

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
    fun toVisitorSite(gson: Gson): VisitorSite {
        return gson.fromJson(data, VisitorSite::class.java)
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
) {
    fun toRecentActivityCell(gson: Gson): RecentActivityCell {
        val evidence = toVisitorEvidence(gson)
        return RecentActivityCell(
            _id,
            RecentActivityCell.Info(evidence.arrive.date, evidence.arrive.time.toString()),
            if (evidence.leave == null) null else RecentActivityCell.Info(evidence.leave.date, evidence.leave.time.toString()),
            siteTitle,
            siteDescription,
            if (evidence.isAutoSignOutActive && evidence.leave == null) AutoSignOutStatus.YES else AutoSignOutStatus.NO,
            evidence.site?.tier?.VISIT_TERMS ?: VisitTerm("Arrive", "Leave")
        )
    }

    fun toVisitorEvidence(gson: Gson): VisitorEvidence = gson.fromJson(data, VisitorEvidence::class.java).apply { this.token = this@VisitorActivityDB.token }
}

// Relation Model
data class VisitorProfileAndSiteDB(
    @Embedded val profile: VisitorProfileDB,
    @Relation(
        parentColumn = "_id",
        entityColumn = "profileId"
    )
    val sites: List<VisitorSiteDB>
)
