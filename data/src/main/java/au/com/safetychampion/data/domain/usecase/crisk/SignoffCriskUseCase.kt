package au.com.safetychampion.data.domain.usecase.crisk // ktlint-disable filename

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.SignaturePayload
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.crisk.CriskTask
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.domain.usecase.action.SignatureView
import au.com.safetychampion.data.domain.usecase.action.SignoffParams

class SignoffCriskUseCase(
    repository: ICriskRepository
) : BaseSignoffUseCase<CriskSignoffParams, CriskTask>(
    repository
) {
    override fun onPayloadModifications(
        mutablePayload: CriskTask,
        actionLinks: List<ActionLink>?,
        docAttachments: List<DocAttachment>?,
        signatures: Pair<List<DocAttachment>, List<SignaturePayload>>?
    ) {
        // TODO("")
        super.onPayloadModifications(mutablePayload, actionLinks, docAttachments, signatures)
    }

    override suspend fun invoke(params: CriskSignoffParams): Result<SignoffStatus> {
        return signoff(params)
    }
}

class CriskSignoffParams(
    val criskID: String,
    override val attachmentList: List<Attachment>,
    override val moduleName: ModuleName,
    override val payload: CriskTask,
    override val signaturesList: List<SignatureView>?,
    override val title: String
) : SignoffParams()
