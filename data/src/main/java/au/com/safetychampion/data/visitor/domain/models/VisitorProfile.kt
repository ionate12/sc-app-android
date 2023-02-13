package au.com.safetychampion.data.visitor.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by @Minh_Khoi_MAI on 22/9/20
 */
@Entity(tableName = "VisitorProfiles")
data class VisitorProfileEntity(
    val phone: String?,
    @PrimaryKey val _id: String,
    val email: String?,
    val name: String,
    val phoneCountryCode: String?
) {
    fun toVisitorProfile(): VisitorProfile {
        return VisitorProfile(
            _id = this._id,
            email = this.email,
            name = this.name,
            phoneCountryCode = this.phoneCountryCode,
            phoneNumber = this.phone,
            address = null,
            company = null
        )
    }
}

data class VisitorProfile(
    var address: String?,
    var company: String?,
    var phoneNumber: String?,
    var _id: String = "",
    var email: String?,
    var name: String?,
    var phoneCountryCode: String? = "(+61)"
) {

    fun toProfileEntity() = VisitorProfileEntity(
        _id = _id,
        name = name ?: "",
        email = email,
        phone = phoneNumber,
        phoneCountryCode = phoneCountryCode
    )

    fun toPii(): VisitorPayload.Pii {
        return VisitorPayload.Pii(
            name = this.name ?: "",
            email = this.email ?: "",
            phone = this.phoneNumber ?: "",
            phoneCountryCode = this.phoneCountryCode ?: ""
        )
    }

    fun toPayload(role: VisitorRole): VisitorPayload.Visitor {
        return VisitorPayload.Visitor(role = role, pii = toPii())
    }
    fun toPhoneString() = "$phoneCountryCode $phoneNumber"
}
