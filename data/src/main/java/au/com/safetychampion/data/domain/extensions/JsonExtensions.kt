package au.com.safetychampion.data.domain.extensions

import au.com.safetychampion.data.domain.core.SCError
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

fun <T> JsonElement.asT(tClass: Class<T>): au.com.safetychampion.data.domain.core.Result<T> {
    val gson = Gson() // inject
    return try {
        val data: T = gson.fromJson(this, tClass)
        au.com.safetychampion.data.domain.core.Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        au.com.safetychampion.data.domain.core.Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

fun <T> String.asT(tClass: Class<T>): au.com.safetychampion.data.domain.core.Result<T> {
    val gson = Gson() // inject
    return try {
        val data: T = gson.fromJson(this, tClass)
        au.com.safetychampion.data.domain.core.Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        au.com.safetychampion.data.domain.core.Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

fun <T> JsonElement.asListT(tClass: Class<T>): au.com.safetychampion.data.domain.core.Result<List<T>> {
    val gson = Gson() // inject
    return try {
        val data: List<T> = gson.fromJson(this, TypeToken.getParameterized(List::class.java, tClass).type)
        au.com.safetychampion.data.domain.core.Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        au.com.safetychampion.data.domain.core.Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

fun <T> String.asListT(tClass: Class<T>): au.com.safetychampion.data.domain.core.Result<List<T>> {
    val gson = Gson() // inject
    return try {
        val data: List<T> = gson.fromJson(this, TypeToken.getParameterized(List::class.java, tClass).type)
        au.com.safetychampion.data.domain.core.Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        au.com.safetychampion.data.domain.core.Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

fun jsonObjectOf(jsonString: String): JsonObject? {
    val gson = Gson() // inject
    return try {
        gson.fromJson(jsonString, JsonObject::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun jsonObjectOf(obj: Any): JsonObject? {
    val gson = Gson() // inject
    return try {
        gson.toJsonTree(obj).asJsonObject
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun jsonObjectOf(vararg pair: Pair<String, JsonElement?>): JsonObject {
    val jsonObj = JsonObject()
    pair.forEach {
        jsonObj.add(it.first, it.second)
    }
    return jsonObj
}

fun jsonArrayOf(list: List<Any>): JsonArray? {
    val gson = Gson() // inject
    return try {
        gson.toJsonTree(list).asJsonArray
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun jsonArrayOf(jsonArrString: String): JsonArray? {
    val gson = Gson() // inject
    return try {
        gson.fromJson(jsonArrString, JsonArray::class.java).asJsonArray
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Any.asJson(): String? {
    val gson = Gson() // inject
    return try {
        gson.toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
