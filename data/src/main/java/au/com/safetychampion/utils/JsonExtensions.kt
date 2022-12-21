package au.com.safetychampion.utils

import au.com.safetychampion.data.di.retrofit.IGson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import timber.log.Timber

val gson = getKoinInstance<IGson>().gson

inline fun <reified T> JsonElement.listOrEmpty(): List<T> {
    val type = object : TypeToken<List<T>>() {}.type
    return try {
        gson.fromJson(this, type)
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.e(e)
        emptyList()
    }
}

inline fun <reified T> JsonElement.itemOrNull(): T? {
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.e(e)
        null
    }
}

inline fun <reified T> String.itemOrNull(): T? {
    return try {
        gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.e(e)
        null
    }
}

inline fun <reified T> String.listOrEmpty(): List<T> {
    return try {
        val type = object : TypeToken<List<T>>() {}.type
        gson.fromJson(this, type)
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.e(e)
        emptyList()
    }
}

fun Any.asJson(): String? {
    return try {
        gson.toJsonTree(this).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
