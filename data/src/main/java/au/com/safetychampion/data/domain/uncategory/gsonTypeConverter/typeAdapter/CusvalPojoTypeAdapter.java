package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo;

public class CusvalPojoTypeAdapter extends TypeAdapter<CusvalPojo> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() != CusvalPojo.class) {
                return null;
            }
            TypeAdapter<CusvalPojo> delegate = (TypeAdapter<CusvalPojo>) gson.getDelegateAdapter(this, type);
            return (TypeAdapter<T>) new CusvalPojoTypeAdapter(delegate);
        }
    };

    private final TypeAdapter<CusvalPojo> delegate;

    CusvalPojoTypeAdapter(TypeAdapter<CusvalPojo> delegate) {
        this.delegate = delegate;
    }

    @Override public void write(JsonWriter out, CusvalPojo value) throws IOException {
        boolean serializeNulls = out.getSerializeNulls();
        out.setSerializeNulls(true);
        try {
            delegate.write(out, value);
        } finally {
            out.setSerializeNulls(serializeNulls);
        }
    }

    @Override public CusvalPojo read(JsonReader in) throws IOException {
        return delegate.read(in);
    }
}
