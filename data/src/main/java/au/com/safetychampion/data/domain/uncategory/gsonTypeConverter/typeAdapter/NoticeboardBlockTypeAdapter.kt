package au.com.safetychampion.scmobile.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.noticeboard.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.uncategory.GsonHelper
import au.com.safetychampion.data.domain.uncategory.getGson
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class NoticeboardBlockTypeAdapter :
    JsonDeserializer<NoticeboardBlock>,
    JsonSerializer<NoticeboardBlock> {
    private val gson by lazy { getGson() }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): NoticeboardBlock {
        if (json == null || !json.isJsonObject) {
            return NoticeboardBlock(null, null)
        }

        val jsonObj = json.asJsonObject
        val typeOfBlock = jsonObj.get("type")?.asString ?: ""

        val data = jsonObj.get("data")

        when (typeOfBlock) {
            TYPE_VDOC ->
                return NoticeboardBlock(typeOfBlock, gson.fromJson(data, VDocBlockData::class.java))

            TYPE_ATTACHMENT ->
                return NoticeboardBlock(typeOfBlock, gson.fromJson(data, BlockData::class.java))

            TYPE_YOUTUBE ->
                return NoticeboardBlock(typeOfBlock, gson.fromJson(data, XYoutubeBlockData::class.java))

            TYPE_EXTERNAL_LINK ->
                return NoticeboardBlock(typeOfBlock, gson.fromJson(data, ExternalLinkBlockData::class.java))

            else ->
                return NoticeboardBlock(typeOfBlock, null)
        }
    }

    override fun serialize(src: NoticeboardBlock?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return GsonHelper.CLEAN_INSTANCE.toJsonTree(src)
    }
}
