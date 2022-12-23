package common.storage.json;

import com.google.gson.Gson;

public class JsonUtils {

    private static Gson gson;

    public static Gson create() {
        gson = new Gson();
        return gson;
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Class<?> fromJson(String json, Class<?> classOfT) {
        return (Class<?>) gson.fromJson(json, classOfT);
    }
}
