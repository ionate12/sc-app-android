package au.com.safetychampion.data.util.extension

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.util.koinGet
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.reflect.TypeToken
import timber.log.Timber

val gson = koinGet<IGsonManager>().gson

inline fun <reified T> JsonElement.listOrEmpty(): List<T> {
    val type = object : TypeToken<List<T>>() {}.type
    return try {
        gson.fromJson(this, type)
    } catch (e: Exception) {
        Timber.e(e)
        emptyList()
    }
}

inline fun <reified T> JsonElement.itemOrNull(): T? {
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

inline fun <reified T> String.itemOrNull(): T? {
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

inline fun <reified T> String.listOrEmpty(): List<T> {
    return try {
        val type = object : TypeToken<List<T>>() {}.type
        gson.fromJson(this, type)
    } catch (e: Exception) {
        Timber.e(e)
        emptyList()
    }
}

fun Any.toJsonString(): String? {
    return try {
        gson.toJson(this)
    } catch (e: Exception) {
        null
    }
}

fun JsonElement?.isNullOrEmpty(): Boolean {
    if (this == null) return true
    return when (this) {
        is JsonPrimitive -> asString.trim().isEmpty()
        is JsonArray -> asJsonArray.isEmpty
        is JsonObject -> asJsonObject.entrySet().isEmpty()
        else -> true
    }
}
