package au.com.safetychampion.data.domain.extensions

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import timber.log.Timber

fun <T> JsonElement.asT(tClass: Class<T>): Result<T> {
    val gson = Gson() // inject
    return try {
        val data: T = gson.fromJson(this, tClass)
        Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

fun <T> String.asT(tClass: Class<T>): Result<T> {
    val gson = Gson() // inject
    return try {
        val data: T = gson.fromJson(this, tClass)
        Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

// Thử follow theo cách này
// Ko cần Result vì ko cần thiết JsonSyntaxException thường sẽ ko xảy ra trừ khi code lỗi.
// Reified T để đưa thêm reflection vào sẽ dùng được typeToken
inline fun <reified T> JsonElement.listOrEmpty(): List<T> {
    val gson = Gson() // Inject
    val type = object : TypeToken<List<T>>() {}.type
    return try {
        gson.fromJson(this, type)
    } catch (e: Exception) {
        Timber.e(e) // Dùng timber thay vì Logd hoặc stacktrace
        emptyList()
    }
}

fun <T> JsonElement.asListT(tClass: Class<T>): Result<List<T>> {
    val gson = Gson() // inject
    return try {
        val data: List<T> = gson.fromJson(this, TypeToken.getParameterized(List::class.java, tClass).type)
        Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(
            SCError.JsonSyntaxException(
                detailsMsg = e.message!!
            )
        )
    }
}

fun <T> String.asListT(tClass: Class<T>): Result<List<T>> {
    val gson = Gson() // inject
    return try {
        val data: List<T> = gson.fromJson(this, TypeToken.getParameterized(List::class.java, tClass).type)
        Result.Success(data)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(
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
