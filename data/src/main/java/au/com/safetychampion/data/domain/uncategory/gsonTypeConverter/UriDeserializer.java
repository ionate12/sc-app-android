package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter;

import android.net.Uri;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class UriDeserializer implements JsonDeserializer<Uri> {
    @Override
    public Uri deserialize(final JsonElement src, final Type srcType,
                           final JsonDeserializationContext context) throws JsonParseException {
        if(isEmptyObject(src)){
            return null;
        }
        return Uri.parse(src.getAsString());
    }

    private boolean isEmptyObject(JsonElement element){
        if(element.isJsonObject()){
            JsonObject obj = element.getAsJsonObject();
            return obj.entrySet().size() == 0;
        }
        return false;
    }
}