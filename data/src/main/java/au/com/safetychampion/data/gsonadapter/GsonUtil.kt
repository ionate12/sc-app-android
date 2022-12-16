package au.com.safetychampion.data.gsonadapter

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.BasePLTypeAdapter
import com.google.gson.GsonBuilder

object GsonUtil {
    val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeHierarchyAdapter(BasePL::class.java, BasePLTypeAdapter())
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
    }
}

fun gson() = GsonUtil.gsonBuilder.create()
