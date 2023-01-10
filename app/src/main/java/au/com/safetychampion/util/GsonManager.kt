package au.com.safetychampion.util

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.domain.models.customvalues.BaseCustomValue
import au.com.safetychampion.data.domain.models.customvalues.CustomValueOption
import au.com.safetychampion.data.domain.models.customvalues.CusvalType
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.CustomValueOptionTypeAdapter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.CustomValueTypeAdapter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.CusvalTypeTypeAdapter
import au.com.safetychampion.util.gsonadapter.BasePLTypeAdapter
import au.com.safetychampion.util.gsonadapter.TierTypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface IGsonManager {
    val gsonBuilder: GsonBuilder
    val gson: Gson
    val cleanGson: Gson
    val nullGson: Gson
}

class GsonManager : IGsonManager {
    override val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeHierarchyAdapter(BasePL::class.java, BasePLTypeAdapter())
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
            .registerTypeAdapter(CustomValueOption::class.java, CustomValueOptionTypeAdapter())
            .registerTypeAdapter(BaseCustomValue::class.java, CustomValueTypeAdapter())
            .registerTypeAdapter(CusvalType::class.java, CusvalTypeTypeAdapter())
    }
    override val gson: Gson by lazy { gsonBuilder.create() }
    override val cleanGson by lazy { Gson() }

    override val nullGson: Gson by lazy { GsonBuilder().serializeNulls().create() }
}
