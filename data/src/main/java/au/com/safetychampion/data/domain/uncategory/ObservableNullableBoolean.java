package au.com.safetychampion.data.domain.uncategory;

import androidx.databinding.ObservableInt;

public class ObservableNullableBoolean extends ObservableInt {
    public static final int NULL = 0;
    public static final int FALSE = -1;
    public static final int TRUE = 1;

    public ObservableNullableBoolean(int value) {
        super(value);
    }

    public ObservableNullableBoolean() {
        super(0);
    }

    public boolean getAsBoolean() {
        return get() == TRUE;
    }

    public void setFromBoolean(Boolean aBoolean) {
        if (aBoolean == null) {
            set(0);
        } else {
            set(aBoolean ? 1 : -1);
        }
    }

    @Override
    public int get() {
        return super.get();
    }

    @Override
    public void set(int value) {
        super.set(value);
    }
}
