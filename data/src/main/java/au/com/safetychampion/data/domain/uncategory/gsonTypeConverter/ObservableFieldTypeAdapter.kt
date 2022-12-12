package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter

import androidx.databinding.ObservableField
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ObservableFieldTypeAdapter : JsonDeserializer<ObservableField<Any>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableField<Any>? {
        return null
    }
}
