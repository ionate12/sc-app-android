package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.safetychampion.data.domain.uncategory.JsonConverter;

public class LiveDataTypeConverter implements JsonDeserializer<MutableLiveData>, JsonSerializer<MutableLiveData> {

    @Override
    public MutableLiveData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Object obj = null;
        try {
            obj = JsonConverter.fromJSON(json, getInnerType(typeOfT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MutableLiveData(obj);
    }

    @Override
    public JsonElement serialize(MutableLiveData src, Type typeOfSrc, JsonSerializationContext context) {
        return JsonConverter.toJsonTree(src.getValue());
    }

    private Type getInnerType(Type typeOfT) throws ClassNotFoundException {
        List<String> str = Arrays.asList(TypeToken.get(typeOfT).toString().replace(">", "")
                .split("<"));
        Class<?> base = Class.forName(str.get(1));
        List<Class<?>> classList = new ArrayList<>();
        for (int i = 2; i < str.size(); i++) {
            classList.add(Class.forName(str.get(i)));
        }
        TypeToken token = TypeToken.getParameterized(base, classList.toArray(new Class<?>[classList.size()]));
        return token.getType();
    }
}
