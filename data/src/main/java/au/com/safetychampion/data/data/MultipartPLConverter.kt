package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.manager.IFileManager
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.util.extension.toMultiPart
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

internal open class MultipartPLConverter {
    private val gsonManager by koinInject<IGsonManager>()
    private val fileManager: IFileManager by koinInject()

    open fun toJsonObject(basePL: BasePL): JsonObject {
        return gsonManager.gson.toJsonTree(basePL).asJsonObject
    }

    suspend operator fun invoke(basePL: BasePL): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        val jsonObject = toJsonObject(basePL)
        val attachmentPLs: MutableList<AttachmentPL> = mutableListOf()
        // 1. CustomValues
        jsonObject.addCustomValues(basePL)
        // 2. ForceNullValues
        (basePL as? IForceNullValues)?.forceNullKeys?.let { list ->
            list.forEach { jsonObject.add(it, JsonNull.INSTANCE) }
            jsonObject.remove("forceNullKeys")
        }

        // 3. Attachments
        (basePL as? IAttachment)?.attachments?.let {
            // add attachmentPL
            attachmentPLs.addAll(it.toAttachmentPL())

            // add attachment data to parts
            parts.addAll(
                it.mapNotNull { it as? Attachment.New }
                    .toMultipartBody(fileManager)
            )
        }

        // 4. Signatures + combine it with attachmentPL
        (basePL as? ISignature)?.signatures?.let {
            jsonObject.add("signatures", it.toSignaturePL())

            attachmentPLs.addAll(it.toAttachmentSignaturePL())

            it.forEach {
                parts.add(it.bitmap.toMultiPart(parts.size.toString(), it.payloadName()))
            }
        }

        // 5. Add AttachmentPL to jsonObject
        jsonObject.add("attachments", gsonManager.cleanGson.toJsonTree(attachmentPLs))

        // 6. remove pending actions value
        jsonObject.remove("pendingActions")

        // Finally convert to requestBody
        val requestBody = gsonManager.nullGson.toJson(jsonObject)
            .toRequestBody("multipart/form-data".toMediaTypeOrNull())

        return listOf(
            MultipartBody.Part.createFormData("json", null, requestBody)
        ) + parts
    }

    private fun JsonObject.addCustomValues(from: BasePL) {
        // 1. Add Cusvals if needed
        (from as? ICusval)?.cusvals?.toPL()?.let {
            this.add("cusvals", it)
        }
        // 2. Add catCusvals if needed
        (from as? ICategoryCusval)?.categoryCusvals?.toPL()?.let {
            this.add("categoryCusvals", it)
        }
        // 3. Add subcatCusvals if needed
        (from as? ISubcategoryCusval)?.subcategoryCusvals?.toPL()?.let {
            this.add("subcategoryCusvals", it)
        }

        (from as? IEnvCusval)?.propOrEnvDamageCusvals?.toPL()?.let {
            this.add("propOrEnvDamageCusvals", it)
        }
    }

    private fun List<CustomValue>.toPL(): JsonElement = map {
        it.toPayload()
    }.let {
        gsonManager.nullGson.toJsonTree(it)
    }

    private fun List<Attachment>.toAttachmentPL() = mapNotNull {
        AttachmentPL.from(it, fileManager)
    }

    private fun List<Signature>.toSignaturePL(): JsonElement = map {
        JsonObject().apply {
            addProperty("_id", it._id)
            addProperty("name", it.name)
        }
    }.let { gsonManager.gson.toJsonTree(it) }

    private fun List<Signature>.toAttachmentSignaturePL() = map {
        AttachmentPL(
            group = it._id,
            fileName = it.payloadName()
        )
    }
}

private data class AttachmentPL(
    val _id: String? = null,
    val fileName: String? = null,
    val group: String? = null,
    val delete: Boolean? = null
) {
    companion object {
        fun from(attachment: Attachment, fileManager: IFileManager) = with(attachment) {
            when (attachment) {
                is Attachment.New -> {
                    AttachmentPL(
                        fileName = fileManager.getFileName(attachment.uri),
                        group = attachment.group
                    )
                }
                is Attachment.Review -> {
                    if (attachment.delete) {
                        AttachmentPL(
                            delete = true,
                            group = attachment.group,
                            _id = attachment._id
                        )
                    } else { null }
                }
                else -> { null }
            }
        }
    }
}
