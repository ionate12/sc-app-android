package au.com.safetychampion.data.domain.uncategory;

public interface GeneralCallback {
    default void onCompleted() {}
    default <T> void onCompleted(T model){}

    default <T> void onError(T msg) {}
}
