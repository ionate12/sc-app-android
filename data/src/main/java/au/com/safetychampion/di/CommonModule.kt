package au.com.safetychampion.data.di.retrofit

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.utils.gsonadapters.BasePLTypeAdapter
import au.com.safetychampion.utils.gsonadapters.TierTypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf<IGson>(::GsonManager)
}

private class GsonManager : IGson {
    override val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeHierarchyAdapter(BasePL::class.java, BasePLTypeAdapter())
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
    }
    override val gson: Gson by lazy { gsonBuilder.create() }
    override val cleanGson by lazy { Gson() }
}

interface IGson {
    val gsonBuilder: GsonBuilder
    val gson: Gson
    val cleanGson: Gson
}
