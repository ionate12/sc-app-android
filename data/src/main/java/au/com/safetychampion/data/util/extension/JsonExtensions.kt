package au.com.safetychampion.data.util.extension

import au.com.safetychampion.data.domain.manager.IGsonManager
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

/**
 * A convenient extension function for Json deserialization.
 * @param customGson Gson with custom adapter implementations, if this param is null, the default gson instance will be used.
 * @throws JsonSyntaxException
 */
@Throws(JsonSyntaxException::class)
inline fun <reified T> JsonElement.parseObject(
    customGson: Gson? = null
): T {
    val type = object : TypeToken<T>() {}.type
    return (customGson ?: koinGet<IGsonManager>().gson).fromJson(this, type)
}

/** @see parseObject */
@Throws(JsonSyntaxException::class)
inline fun <reified T> String.parseObject(
    customGson: Gson? = null
): T? {
    val type = object : TypeToken<T>() {}.type
    return (customGson ?: koinGet<IGsonManager>().gson).fromJson(this, type)
}

/**
 * A convenient extension function for json serialization.
 * @param customGson Gson with custom adapter implementations, if this param is null, the default gson instance will be used.
 */

fun Any.toJsonString(
    customGson: Gson? = null
): String? {
    return (customGson ?: koinGet<IGsonManager>().gson).toJson(this@toJsonString)
}

fun jsonObjectOf(src: Any, customGson: Gson? = null): JsonObject? {
    return jsonElementOf(src, customGson)?.asJsonObject
}

fun jsonElementOf(src: Any, customGson: Gson? = null): JsonElement? {
    return (customGson ?: koinGet<IGsonManager>().gson).toJsonTree(src)
}

/**
 * @return true if this JsonObject is null or its string representation is equal to "{}", false otherwise.
 */
fun JsonObject?.isNullOrEmpty(): Boolean {
    return this == null || this.toString() == "{}"
}

fun JsonElement?.asStringOrNull(): String? = try {
    this?.asString
} catch (e: Exception) { null }

fun JsonElement?.asIntOrNull(): Int? = try {
    this?.asInt
} catch (e: Exception) { null }

fun JsonElement?.asBooleanOrDefault(default: Boolean = false): Boolean = try {
    this?.asBoolean ?: default
} catch (e: Exception) { default }
