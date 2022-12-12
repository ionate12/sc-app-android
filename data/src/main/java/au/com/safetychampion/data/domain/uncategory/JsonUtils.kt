package au.com.safetychampion.data.domain.uncategory

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.Exception
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

object JsonUtils {
    fun getStringProperty(obj: JsonObject, memberName: String?): String? {
        return try {
            obj[memberName].asString
        } catch (e: Exception) {
            null
        }
    }

    fun getBooleanProperty(obj: JsonObject, memberName: String?): Optional<Boolean> {
        var b: Boolean
        return try {
            Optional.of(obj[memberName].asBoolean)
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    fun getObjectProperty(obj: JsonObject, memberName: String?): JsonObject? {
        return try {
            obj[memberName].asJsonObject
        } catch (e: Exception) {
            null
        }
    }

    fun getOrCreateObject(obj: JsonObject, memberName: String): JsonObject {
        var temp = obj.getAsJsonObject(memberName)
        if (memberName == "") temp = obj
        if (temp == null) {
            temp = JsonObject()
            obj.add(memberName, temp)
        }
        return temp
    }

    fun getOrCreateElement(obj: JsonObject, memberName: String?, ifNull: JsonElement): JsonElement {
        return if (obj.has(memberName)) {
            obj[memberName]
        } else {
            obj.add(memberName, ifNull)
            ifNull
        }
    }

    fun getOrCreateArray(obj: JsonObject, memberName: String?): JsonArray {
        var temp = obj.getAsJsonArray(memberName)
        if (temp == null) {
            temp = JsonArray()
            obj.add(memberName, temp)
        }
        return temp
    }

    /**
     * If key exists -> Replace
     *
     */
    fun dynamicAdd(array: JsonArray, obj: JsonObject?, key: String?, value: String): Boolean {
        var check = true
        for (i in 0 until array.size()) {
            val x = array[i]
            if (x.isJsonObject && x.asJsonObject.has(key) &&
                x.asJsonObject[key].asString == value
            ) {
                check = false
                break
            }
        }
        if (check) {
            array.add(obj)
        }
        return check
    }

    fun objectTransformer(obj: JsonObject, vararg memberNames: String?): JsonObject {
        val nObj = JsonObject()
        obj.entrySet().stream().filter { (key): Map.Entry<String, JsonElement?> ->
            Arrays.asList(*memberNames).contains(
                key
            )
        }
            .forEach { (key, value): Map.Entry<String?, JsonElement?> ->
                nObj.add(
                    key,
                    value
                )
            }
        return nObj
    }

    fun arrayFind(array: JsonArray, member: String?, value: String): Stream<JsonObject> {
        val jsonObjectList =
            IntStream.range(0, array.size()).mapToObj { i: Int -> array[i].asJsonObject }
                .collect(Collectors.toList())
        return jsonObjectList.stream().filter { obj: JsonObject ->
            if (obj.has(member)) return@filter obj[member].asString == value
            false
        }
    }

    fun isEmptyObject(element: JsonElement): Boolean {
        if (element.isJsonObject) {
            val obj = element.asJsonObject
            return obj.entrySet().size == 0
        }
        return false
    }
}
