package au.com.safetychampion.util

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.util.gsonadapter.BasePLTypeAdapter
import au.com.safetychampion.util.gsonadapter.TierTypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface IGsonManager {
    val gsonBuilder: GsonBuilder
    val gson: Gson
    val cleanGson: Gson
}

class GsonManager : IGsonManager {
    override val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeHierarchyAdapter(BasePL::class.java, BasePLTypeAdapter())
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
    }
    override val gson: Gson by lazy { gsonBuilder.create() }
    override val cleanGson by lazy { Gson() }
}
