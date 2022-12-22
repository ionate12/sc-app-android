package au.com.safetychampion.data.domain.extensions

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import timber.log.Timber

inline fun <reified T> JsonElement.listOrEmpty(): List<T> {
    val gson = Gson() // Inject
    val type = object : TypeToken<List<T>>() {}.type
    return try {
        gson.fromJson(this, type)
    } catch (e: Exception) {
        Timber.e(e)
        emptyList()
    }
}

inline fun <reified T> JsonElement.itemOrNull(): T? {
    val gson = Gson() // Inject
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

inline fun <reified T> String.itemOrNull(): T? {
    val gson = Gson() // inject
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

inline fun <reified T> String.listOrEmpty(): List<T> {
    val gson = Gson() // inject
    return try {
        val type = object : TypeToken<List<T>>() {}.type
        gson.fromJson(this, type)
    } catch (e: Exception) {
        Timber.e(e)
        emptyList()
    }
}

fun Any.asJson(): String? {
    val gson = Gson() // inject
    return try {
        gson.toJsonTree(this).toString()
    } catch (e: Exception) {
        null
    }
}
