package au.com.safetychampion.data.domain.usecase.action

import android.graphics.Bitmap
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.SignaturePayload
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase

class SignoffActionUseCase(repository: IActionRepository) : BaseSignoffUseCase<ActionSignoffParams, ActionTask>(repository) {
    override fun onPayloadModifications(
        mutablePayload: ActionTask,
        actionLinks: List<ActionLink>?,
        docAttachments: List<DocAttachment>?,
        signatures: Pair<List<DocAttachment>, List<SignaturePayload>>?
    ) {
        if (actionLinks != null) {
            mutablePayload.links?.clear()
            mutablePayload.links?.addAll(actionLinks)
        }

        mutablePayload.attachment?.clear()
        if (docAttachments != null) {
            mutablePayload.attachment?.addAll(docAttachments)
        }

//        if (signatures != null) {
//            mutableParams.attachment.addAll(signatures.first)
//            mutableParams.signaturePayload.clear()
//            mutableParams.signaturePayload.addAll(signatures.second)
//        }
    }
    override suspend fun invoke(params: ActionSignoffParams): Result<SignoffStatus> {
        return signoff(params)
    }
}

class ActionSignoffParams(
    val actionId: String,
    override val attachmentList: List<Attachment>,
    override val moduleName: ModuleName,
    override val payload: ActionTask,
    override val signaturesList: List<SignatureView>?,
    override val title: String
) : SignoffParams()

/** Base class for all signoff call */
abstract class SignoffParams {

    /** Task id, used to retrieve offline task */
    final val id: String get() = payload._id ?: ""

    abstract val attachmentList: List<Attachment>

    abstract val moduleName: ModuleName

    /** Payload for signoff */
    abstract val payload: BaseTask

    abstract val signaturesList: List<SignatureView>?

    abstract val title: String

    /** Pending action, will be submit before sign off. Can be null */
    open val pendingAction: List<PendingActionPL>? = null

    val offlineTitle: String get() = if (payload.complete == true) "[SIGN-OFF]" else "[SAVE] ${moduleName.value}: $title"
}

class OfflineTask(
    var title: String = "",
    var status: String = ""
)

class SignatureView(
    val bitmap: Bitmap,
    var name: String
)
