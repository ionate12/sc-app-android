package au.com.safetychampion.data.domain.models.login

import androidx.room.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.admin.Admin
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.JsonArrayTypeConverter
import com.google.gson.JsonArray
import java.io.Serializable

data class LoginUser(
    @PrimaryKey
    var _id: String,
    var type: String? = null,
    var tierId: String? = null,
    var name: String? = null,
    var email: String? = null,

    // Current TierId will be set when morph down or unmorph
    @JvmField
    var currentTierId: String? = null,

    @JvmField
    @TypeConverters(JsonArrayTypeConverter::class)
    var configuration: JsonArray? = null,

    @JvmField
    @Ignore
    var tier: Tier? = null,

    @Ignore
    var adminConf: Admin? = null
) : Serializable {
    override fun toString(): String {
        return "LoginUser{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}'
    }
}
