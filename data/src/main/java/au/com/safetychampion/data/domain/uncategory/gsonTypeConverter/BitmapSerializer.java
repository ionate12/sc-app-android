package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter;

import android.graphics.Bitmap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import au.com.safetychampion.data.domain.uncategory.GeneralConverter;

public class BitmapSerializer implements JsonSerializer<Bitmap>, JsonDeserializer<Bitmap> {


    @Override
    public Bitmap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsString();
        return GeneralConverter.getBitmapFromString(s);
    }

    @Override
    public JsonElement serialize(Bitmap src, Type typeOfSrc, JsonSerializationContext context) {
        String s = GeneralConverter.getStringFromBitmap(src);
        return new JsonPrimitive(s);
    }
}

