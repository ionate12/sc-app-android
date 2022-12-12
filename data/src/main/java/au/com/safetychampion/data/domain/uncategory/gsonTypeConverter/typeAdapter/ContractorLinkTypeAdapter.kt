package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.contractor.ContractorLink
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkReviewPlan
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkSafetyPlan
import au.com.safetychampion.data.domain.uncategory.TaskType
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ContractorLinkTypeAdapter : JsonDeserializer<ContractorLink> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ContractorLink {
        val gson = GsonBuilder().create()
        val obj = json?.asJsonObject
        if (obj != null && obj.has("type")) {
            when (obj.get("type").asString) {
                TaskType.SAFETY_PLAN -> return gson.fromJson(json, ContractorLinkSafetyPlan::class.java)
                TaskType.REVIEW_PLAN -> return gson.fromJson(json, ContractorLinkReviewPlan::class.java)
            }
        }
        return gson.fromJson(json, ContractorLink::class.java)
    }
}
