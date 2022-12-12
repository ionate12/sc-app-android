package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter;

import androidx.databinding.ObservableField;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ObservableFieldSerializer implements JsonDeserializer<ObservableField<String>> {

    /*@Override
    public ObservableField<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            return (ObservableField<T>) new ObservableField<String>(json.getAsString());
        }
        if(json.isJsonObject()) {
            return new ObservableField<T>(json.getAsJsonObject());
        }

        if(json.isJsonArray()) {
            return new ObservableField<T>(json.getAsJsonArray());
        }
        return null;

    }
*/

    @Override
    public ObservableField<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ObservableField<>(json.getAsString());
    }

   /* @Override
    public JsonElement serialize(ObservableField src, Type typeOfSrc, JsonSerializationContext context) {
        return JsonConverter.toJsonTree(src.get());
    }*/

}
