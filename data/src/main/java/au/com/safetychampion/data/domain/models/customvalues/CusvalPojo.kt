package au.com.safetychampion.data.domain.models.customvalues

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose

data class CusvalPojo(
    var _id: String,
    var title: String,
    var type: String,
    @JvmField
    @Expose
    var value: JsonElement? = null
)
