package au.com.safetychampion.data.domain.models.customvalues

import au.com.safetychampion.data.domain.uncategory.GsonHelper.Companion.getGson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.reflect.TypeToken
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*

interface VerifierCallback {
    fun verified(aBoolean: Boolean)
}

data class CustomValue(
    var _id: String? = null,
    var title: String? = null,
    var type: String? = null,
    var description: String? = null,
    @Transient
    var verifyObserver: Subject<Boolean> = PublishSubject.create(),
    var options: List<CustomValueOption>? = null,
    var errMessage: String? = null,
    var verified: Boolean = false,
    private val verifyCallback: VerifierCallback? = null,
    private var value: JsonElement? = null,
    val required: Boolean = false

) {
    companion object {
        private fun getStringProperty(obj: JsonObject, memberName: String?): String? {
            return try {
                obj[memberName].asString
            } catch (e: Exception) {
                null
            }
        }
        fun fromJson(itemObj: JsonObject): CustomValue {
            val _id = itemObj["_id"].asString
            val title: String? = getStringProperty(itemObj, "title")
            val des: String? = getStringProperty(itemObj, "description")
            val type: String? = getStringProperty(itemObj, "type")

            val value = itemObj["value"]
            val options: List<CustomValueOption>
            val v: CustomValue
            val required: Boolean = Optional.ofNullable(itemObj["required"]).orElse(JsonPrimitive(false)).asBoolean
            if (itemObj.has("options")) {
                options = getGson().fromJson(
                    itemObj["options"],
                    object : TypeToken<List<CustomValueOption?>?>() {}.type
                )
                v = CustomValue(
                    _id = _id,
                    title = title,
                    description = des,
                    type = type,
                    required = required,
                    options = options
                )
            } else {
                v = CustomValue(
                    _id = _id,
                    title = title,
                    description = des,
                    type = type,
                    required = required,
                    options = null
                )
            }
            Optional.ofNullable(value).ifPresent { `val`: JsonElement ->
                if (`val` is JsonNull) {
                    return@ifPresent
                }
                if (type != null && type == "date") {
                    val indexOfT = `val`.asString.indexOf("T")
                    if (indexOfT > 0) {
                        val nValue = `val`.asString.substring(0, indexOfT)
                        v.setValueForce(JsonPrimitive(nValue))
                        return@ifPresent
                    }
                }
                v.setValueForce(`val`)
            }
            return v
        }
    }
    fun setValueForce(element: JsonElement?) {
        if (this.type == CusvalType.DATE && element != null && !element.isJsonNull) {
            val value = element.asString
            if (value.indexOf("T") > 0) {
                this.value = JsonPrimitive(value.substring(0, value.indexOf("T")))
            } else {
                this.value = element
            }
        } else {
            this.value = element
        }
    }
}
