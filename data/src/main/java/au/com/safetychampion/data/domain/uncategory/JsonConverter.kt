package au.com.safetychampion.data.domain.uncategory

import au.com.safetychampion.data.domain.models.FilePart
import au.com.safetychampion.data.domain.models.OfflineTask
import au.com.safetychampion.data.domain.uncategory.GsonHelper.Companion.getGson
import au.com.safetychampion.data.domain.uncategory.GsonHelper.Companion.getGsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import java.lang.Exception
import java.lang.reflect.Type
import java.util.ArrayList

object JsonConverter {
    private const val FORWARD = 0
    private const val BACKWARD = 1

    @JvmStatic
    fun <T> fromJSON(jsonString: String?, clazz: Class<T>?): T? {
        return try {
            val gson = getGson()
            gson.fromJson(jsonString, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> fromJSON(jsonString: String?, clazz: Class<T>?, serialiseNulls: Boolean): T? {
        return try {
            val builder = getGsonBuilder()
            if (serialiseNulls) builder.serializeNulls()
            val gson = builder.create()
            gson.fromJson(jsonString, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> fromJSON(jsonElement: JsonElement?, clazz: Class<T>?): T? {
        return try {
            val gson = getGson()
            gson.fromJson(jsonElement, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun <T> fromJSON(jsonElement: JsonElement?, type: Type?): T? {
        return try {
            val gson = getGson()
            gson.fromJson(jsonElement, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> toJson(`object`: T): String? {
        return try {
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Uri.class, new UriSerializer())
//                .registerTypeAdapter(Bitmap.class, new BitmapSerializer())
//                .create();
            val gson = getGson()
            gson.toJson(`object`)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> toJsonForceNulls(`object`: T): String? {
        return try {
            val gson = getGsonBuilder()
                .serializeNulls()
                .create()
            gson.toJson(`object`)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun toJSON(jsonElement: JsonElement?): String? {
        return try {
            val gson = getGson()
            gson.toJson(jsonElement)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //  public static String toJSON(JsonElement jsonElement, boolean serialiseNulls) {
    //    try {
    //      GsonBuilder builder = new GsonBuilder();
    //      if(serialiseNulls)
    //        builder.serializeNulls();
    //      Gson gson  = builder.create();
    //      return gson.toJson(jsonElement);
    //    } catch (Exception e){
    //      e.printStackTrace();
    //      return null;
    //    }
    //  }
    fun <T> JsonArrayToList(array: JsonArray): List<T>? {
        try {
            val list: MutableList<T> = ArrayList()
            for (i in 0 until array.size()) {
                list.add(array[i] as T)
            }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun searchTreeMultiple(root: JsonElement, key: String?, value: String): List<JsonElement> {
        val storeElement: MutableList<JsonElement> = ArrayList()
        if (root.isJsonArray) {
            val arr = root.asJsonArray
            for (i in 0 until arr.size()) {
                val e = searchTreeMultiple(arr[i], key, value)
                if (e.size > 0) {
                    storeElement.addAll(e)
                }
            }
        } else if (root.isJsonObject) {
            val obj = root.asJsonObject
            if (obj.has(key)) {
                if (obj[key].asString == value) {
                    storeElement.add(obj)
                } else {
                    for ((_, value1) in obj.entrySet()) {
                        val inside = searchTreeMultiple(value1, key, value)
                        if (inside.size > 0) {
                            storeElement.addAll(inside)
                        }
                    }
                }
            } else {
                for ((_, value1) in obj.entrySet()) {
                    val inside = searchTreeMultiple(value1, key, value)
                    if (inside.size > 0) {
                        storeElement.addAll(inside)
                    }
                }
            }
        } else if (root.isJsonPrimitive) {
        } else if (root.isJsonNull) {
        }
        return storeElement
    }

    fun searchTreeMultiple(root: JsonElement, key: String?): List<JsonElement> {
        val storeElement: MutableList<JsonElement> = ArrayList()
        if (root.isJsonArray) {
            val arr = root.asJsonArray
            for (i in 0 until arr.size()) {
                val e = searchTreeMultiple(arr[i], key)
                if (e.size > 0) {
                    storeElement.addAll(e)
                }
            }
        } else if (root.isJsonObject) {
            val obj = root.asJsonObject
            if (obj.has(key)) {
                storeElement.add(obj)
            } else {
                for ((_, value) in obj.entrySet()) {
                    val inside = searchTreeMultiple(value, key)
                    if (inside.size > 0) {
                        storeElement.addAll(inside)
                    }
                }
            }
        } else if (root.isJsonPrimitive) {
        } else if (root.isJsonNull) {
        }
        return storeElement
    }

    fun searchTree(root: JsonElement, key: String?, value: String): JsonElement? {
        var storeElement: JsonElement? = null
        return try {
            if (root.isJsonArray) {
                val arr = root.asJsonArray
                for (i in 0 until arr.size()) {
                    val e = searchTree(arr[i], key, value)
                    if (e != null) {
                        storeElement = e
                        break
                    }
                }
            } else if (root.isJsonObject) {
                val obj = root.asJsonObject
                if (obj.has(key)) {
                    if (obj[key].asString == value) {
                        storeElement = obj
                    } else {
                        var e: JsonElement? = null
                        for ((_, value1) in obj.entrySet()) {
                            val inside = searchTree(value1, key, value)
                            if (inside != null) {
                                e = inside
                                break
                            }
                        }
                        if (e != null) storeElement = e
                    }
                } else {
                    var e: JsonElement? = null
                    for ((_, value1) in obj.entrySet()) {
                        val inside = searchTree(value1, key, value)
                        if (inside != null) {
                            e = inside
                            break
                        }
                    }
                    if (e != null) storeElement = e
                }
            } else if (root.isJsonPrimitive) {
            } else if (root.isJsonNull) {
            }
            storeElement
        } catch (e: Exception) {
            storeElement
        }
    }

    @JvmStatic
    fun <T> toJsonTree(obj: T): JsonElement? {
        return try {
            val gson = getGsonBuilder()
                .create()
            gson.toJsonTree(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> convertToOfflineTask(
        _id: String?,
        userId: String?,
        title: String?,
        type: String?,
        obj: T
    ): OfflineTask? {
        return try {
            val gson = getGson()
            val jsonObject = gson.toJsonTree(obj)
            OfflineTask(_id!!, userId!!, title!!, type!!, jsonObject.asJsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> convertToOfflineTask(
        _id: String?,
        userId: String?,
        title: String?,
        type: String?,
        obj: T,
        formSerialiseNulls: Boolean
    ): OfflineTask? {
        return try {
            val gson = getGson()
            val jsonObject = gson.toJsonTree(obj).asJsonObject
            if (formSerialiseNulls) {
                if (obj is FilePart) {
                    jsonObject.add("form", (obj as FilePart).form)
                }
            }
            OfflineTask(_id!!, userId!!, title!!, type!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> convertToOfflineTask(
        _id: String?,
        userId: String?,
        wpId: String?,
        title: String?,
        type: String?,
        obj: T
    ): OfflineTask? {
        return try {
            val gson = getGson()
            val jsonObject = gson.toJsonTree(obj)
            OfflineTask(_id!!, userId!!, wpId!!, title!!, type!!, jsonObject.asJsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
