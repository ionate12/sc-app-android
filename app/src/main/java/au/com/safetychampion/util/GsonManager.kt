package au.com.safetychampion.util

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.util.gsonadapters.BasePLTypeAdapter
import au.com.safetychampion.data.util.gsonadapters.TierTypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonManager : IGsonManager {
    override val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeHierarchyAdapter(BasePL::class.java, BasePLTypeAdapter())
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
    }
    override val gson: Gson by lazy { gsonBuilder.create() }
    override val cleanGson by lazy { Gson() }
}
