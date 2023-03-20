package au.com.safetychampion.util

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.data.common.OfflineRequest
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.domain.models.customvalues.BaseCustomValue
import au.com.safetychampion.data.domain.models.customvalues.CustomValueOption
import au.com.safetychampion.data.domain.models.customvalues.CusvalType
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.AttachmentTypeAdapter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.CustomValueOptionTypeAdapter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.CustomValueTypeAdapter
import au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.CusvalTypeTypeAdapter
import au.com.safetychampion.data.util.gsonadapters.ModuleTypeConverter
import au.com.safetychampion.data.util.gsonadapters.OfflineRequestTypeConverter
import au.com.safetychampion.data.util.gsonadapters.TierTypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonManager : IGsonManager {
    override val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
            .registerTypeAdapter(ModuleType::class.java, ModuleTypeConverter())
            .registerTypeAdapter(CustomValueOption::class.java, CustomValueOptionTypeAdapter())
            .registerTypeAdapter(BaseCustomValue::class.java, CustomValueTypeAdapter())
            .registerTypeAdapter(CusvalType::class.java, CusvalTypeTypeAdapter())
            .registerTypeAdapter(OfflineRequest::class.java, OfflineRequestTypeConverter())
            .registerTypeAdapter(Attachment::class.java, AttachmentTypeAdapter())
    }
    override val gson: Gson by lazy { gsonBuilder.create() }
    override val cleanGson by lazy { Gson() }

    override val nullGson: Gson by lazy { GsonBuilder().serializeNulls().create() }
}
