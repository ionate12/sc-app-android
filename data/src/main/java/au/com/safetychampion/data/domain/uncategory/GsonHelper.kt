package au.com.safetychampion.data.domain.uncategory

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonHelper {
    companion object {
        @JvmStatic
        fun getGsonBuilder(): GsonBuilder {
            return GsonBuilder()
        }

        @JvmStatic
        fun getGson(): Gson {
            TODO("this function will be removed")
        }

        @JvmStatic
        fun getGsonSerializeNulls(): Gson {
            return getGsonBuilder().serializeNulls().create()
        }

        val CLEAN_INSTANCE = GsonBuilder().create()
    }
}

fun getGson(): Gson = TODO("this function will be removed")
fun getGsonSerializeNulls() = GsonHelper.getGsonSerializeNulls()
