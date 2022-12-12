package au.com.safetychampion.data.gsonadapter

import au.com.safetychampion.data.domain.models.TierType
import com.google.gson.GsonBuilder

object GsonUtil {
    val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
    }
}

fun gson() = GsonUtil.gsonBuilder.create()
