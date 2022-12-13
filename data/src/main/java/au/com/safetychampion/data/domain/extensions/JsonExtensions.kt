package au.com.safetychampion.data.domain.extensions

import au.com.safetychampion.data.domain.core.SCError
import com.google.gson.Gson
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

fun Any.asJson(): String? {
    val gson = Gson() // inject
    return try {
        gson.toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
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
