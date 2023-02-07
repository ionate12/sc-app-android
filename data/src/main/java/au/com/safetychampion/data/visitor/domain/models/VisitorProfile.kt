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
    fun toVisitorProfile() = VisitorProfile(this)
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
    constructor(entity: VisitorProfileEntity) : this(
        _id = entity._id,
        email = entity.email,
        name = entity.name,
        phoneCountryCode = entity.phoneCountryCode,
        phoneNumber = entity.phone,
        address = null,
        company = null
    )
}
