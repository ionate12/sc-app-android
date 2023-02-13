package au.com.safetychampion.data.util.extension

import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.domain.manager.IGsonManager
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

/**
 * A convenient extension function for Json deserialization.
 * @param customGson Gson with custom adapter implementations, if this param is null, the default gson instance will be used.
 * @see deserializeInBackground
 * @throws JsonSyntaxException
 */
@Throws(JsonSyntaxException::class)
suspend inline fun <reified T> JsonElement.parseList(
    customGson: Gson? = null
): List<T> {
    return deserializeInBackground<List<T>>(this@parseList, customGson)
}

/** @see parseList */
@Throws(JsonSyntaxException::class)
suspend inline fun <reified T> String.parseList(
    customGson: Gson? = null
): List<T> {
    return deserializeInBackground<List<T>>(this, customGson)
}

/**
 * A convenient extension function for Json deserialization.
 * @param customGson Gson with custom adapter implementations, if this param is null, the default gson instance will be used.
 * @see deserializeInBackground
 * @throws JsonSyntaxException
 */
@Throws(JsonSyntaxException::class)
suspend inline fun <reified T> JsonElement.parseObject(
    customGson: Gson? = null
): T {
    return deserializeInBackground<T>(this, customGson)
}

/** @see parseObject */
@Throws(JsonSyntaxException::class)
suspend inline fun <reified T> String.parseObject(
    customGson: Gson? = null
): T? {
    return deserializeInBackground<T>(this@parseObject, customGson)
}

/**
 * A convenient extension function for json serialization.
 * @param customGson Gson with custom adapter implementations, if this param is null, the default gson instance will be used.

 */
suspend fun Any.toJsonString(
    customGson: Gson? = null
): String? {
    return withContext(defaultDispatcher) {
        (customGson ?: defaultGson).toJson(this@toJsonString)
    }
}

/* --------------------------------------------------------------------------------------------------------------- */
val defaultGson = koinGet<IGsonManager>().gson
val defaultDispatcher = koinGet<IDispatchers>().default

/**
 * A helper function for deserialization in the background thread.
 * @param json a [JsonElement] that needs to be converted to an object of generic type [T].
 * @param [gson] a [Gson] instance used for deserializing. If this param is null, the default gson instance will use be used.
 * @throws JsonSyntaxException
 * @return An object of generic type [T] that is created from the deserialization of the json object.
 */
@Throws(JsonSyntaxException::class)
suspend inline fun <reified T> deserializeInBackground(json: JsonElement, gson: Gson?): T {
    return withContext(defaultDispatcher) {
        val type = object : TypeToken<T>() {}.type
        (gson ?: defaultGson).fromJson(json, type)
    }
}

/**
 * @see deserializeInBackground
 */
@Throws(JsonSyntaxException::class)
suspend inline fun <reified T> deserializeInBackground(json: String, gson: Gson?): T {
    return withContext(defaultDispatcher) {
        val type = object : TypeToken<T>() {}.type
        (gson ?: defaultGson).fromJson<T>(json, type)
    }
}

/**
 * @return true if this JsonObject is null or its string representation is equal to "{}", false otherwise.
 */
fun JsonObject?.isNullOrEmpty(): Boolean {
    return this == null || this.toString() == "{}"
}
