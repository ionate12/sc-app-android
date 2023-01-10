package au.com.safetychampion.data.domain.manager

import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface IGsonManager {
    val gsonBuilder: GsonBuilder
    val gson: Gson
    val cleanGson: Gson
}
