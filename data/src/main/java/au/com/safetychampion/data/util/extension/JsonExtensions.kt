package au.com.safetychampion.data.util.extension

import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.domain.manager.IGsonManager
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import timber.log.Timber

val gson = koinGet<IGsonManager>().gson
val defaultDispatcher = koinGet<IDispatchers>().default

suspend inline fun <reified T> JsonElement.listOrEmpty(): List<T> {
    return withContext(defaultDispatcher) {
        val type = object : TypeToken<List<T>>() {}.type
        try {
            gson.fromJson(this@listOrEmpty, type)
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }
}

suspend inline fun <reified T> JsonElement.itemOrNull(): T? {
    return withContext(defaultDispatcher) {
        try {
            gson.fromJson(this@itemOrNull, T::class.java)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}

suspend inline fun <reified T> String.itemOrNull(): T? {
    return withContext(defaultDispatcher) {
        try {
            gson.fromJson(this@itemOrNull, T::class.java)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}

suspend inline fun <reified T> String.listOrEmpty(): List<T> {
    return withContext(defaultDispatcher) {
        try {
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson(this@listOrEmpty, type)
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }
}

suspend fun Any.toJsonString(): String? {
    return withContext(defaultDispatcher) {
        try {
            gson.toJson(this@toJsonString)
        } catch (e: Exception) {
            null
        }
    }
}

fun JsonObject?.isNullOrEmpty(): Boolean {
    return this == null || this.toString() == "{}"
}
