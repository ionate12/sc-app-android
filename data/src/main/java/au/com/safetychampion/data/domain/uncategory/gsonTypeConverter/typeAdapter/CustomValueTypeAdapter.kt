package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.customvalues.BaseCustomValue
import au.com.safetychampion.data.domain.models.customvalues.CustomValueCheckbox
import au.com.safetychampion.data.domain.models.customvalues.CustomValueCurrency
import au.com.safetychampion.data.domain.models.customvalues.CustomValueDropdown
import au.com.safetychampion.data.domain.models.customvalues.CustomValueInt
import au.com.safetychampion.data.domain.models.customvalues.CustomValueString
import au.com.safetychampion.data.domain.models.customvalues.CustomValueTime
import au.com.safetychampion.data.domain.models.customvalues.CusvalType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import timber.log.Timber
import java.lang.reflect.Type

class CustomValueTypeAdapter : JsonDeserializer<BaseCustomValue<*>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseCustomValue<*>? {
        if (json == null || !json.isJsonObject || context == null) {
            return null
        }
        return json.asJsonObject.createCusval(context)
    }

    private fun JsonObject.createCusval(context: JsonDeserializationContext): BaseCustomValue<*>? {
        val type = this["type"]?.asString?.let { CusvalType.fromString(it) }
        if (type == null) {
            Timber.e("Can't deserialize Custom value because of missing or invalid \"type\" field.")
            return null
        }
        val data: BaseCustomValue<*> = when (type) {
            CusvalType.Text,
            CusvalType.Email,
            CusvalType.Date,
            CusvalType.Color,
            CusvalType.Url,
            CusvalType.Telephone -> context.deserialize(this, CustomValueString::class.java)
            CusvalType.Number -> context.deserialize(this, CustomValueInt::class.java)
            CusvalType.Time -> context.deserialize(this, CustomValueTime::class.java)
            CusvalType.SingleDrop,
            CusvalType.MultipleDrop -> context.deserialize(this, CustomValueDropdown::class.java)
            CusvalType.Checkbox,
            CusvalType.Radio -> context.deserialize(this, CustomValueCheckbox::class.java)
            CusvalType.Currency -> context.deserialize(this, CustomValueCurrency::class.java)
        }

        if (!this.has("value")) {
            data.trySet(null)
        }
        return data
    }
}

class CusvalTypeTypeAdapter : JsonDeserializer<CusvalType>, JsonSerializer<CusvalType> {
    override fun serialize(
        src: CusvalType?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        return src?.value?.let { JsonPrimitive(it) }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CusvalType = json?.asString?.let { CusvalType.fromString(it) } ?: CusvalType.Text
}
