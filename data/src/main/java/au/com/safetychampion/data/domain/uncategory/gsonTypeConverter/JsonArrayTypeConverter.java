package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter;

import androidx.room.TypeConverter;

import com.google.gson.JsonArray;

import au.com.safetychampion.data.domain.uncategory.JsonConverter;

public class JsonArrayTypeConverter {
  @TypeConverter
  public static JsonArray toJsonArray(String data){
    return JsonConverter.fromJSON(data, JsonArray.class);
  }
@TypeConverter
  public static String fromJsonArray(JsonArray array){
    return JsonConverter.toJSON(array);
  }
}
