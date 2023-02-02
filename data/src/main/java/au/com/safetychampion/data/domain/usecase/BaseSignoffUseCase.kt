package au.com.safetychampion.data.domain.usecase

import au.com.safetychampion.data.data.common.TaskDAO
import au.com.safetychampion.data.domain.SignaturePayload
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.export
import au.com.safetychampion.data.domain.manager.IFileManager
import au.com.safetychampion.data.domain.manager.IOfflineConverter
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import au.com.safetychampion.data.domain.usecase.action.CreatePendingActionAsynchronousUseCase
import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.SignoffParams
import au.com.safetychampion.data.util.extension.koinInject
import java.util.*

interface ISignoffGeneral<T : SignoffParams> {
    suspend fun signoff(params: T): Result<SignoffStatus.OnlineCompleted>
    suspend fun save(params: T): Result<SignoffStatus.OnlineSaved>
}

abstract class BaseSignoffUseCase<T : SignoffParams, P : BaseTask>(
    private val repository: ISignoffGeneral<T>
) : BaseUseCase() {

    private val offlineTaskConverter by koinInject<IOfflineConverter>()

    private val fileContentManager: IFileManager by koinInject()

    private val taskDAO by koinInject<TaskDAO>()

    private fun newOfflineTask(data: T): OfflineTask {
        return offlineTaskConverter.toOfflineTask(data)
    }

    private fun overwrite(param: T): Result<SignoffStatus.OfflineCompleted>? {
        return taskDAO.getOfflineTask(
            taskId = param.id
        )?.let {
            taskDAO.insertOfflineTask(
                // TODO("overwrite here")
                it
            )
            return Result.Success(
                SignoffStatus.OfflineCompleted(
                    title = param.title,
                    moduleName = param.moduleName.value,
                    isOverwritten = true
                )
            )
        }
    }

    private suspend fun signoffOrSave(param: T): Result<SignoffStatus> {
        return if (param.payload.complete == true) {
            repository.signoff(param)
        } else {
            repository.save(param)
        }
    }

    private val createPendingAction: CreatePendingActionAsynchronousUseCase by koinInject()

    private suspend fun signoffInternal(param: T): Result<SignoffStatus> {
        onPayloadModifications(
            mutablePayload = param.payload as P,
            // 1a. Create pending action
            actionLinks = createPendingAction
                .invoke(param.pendingAction)
                .dataOrNull(),
            docAttachments = param
                .attachmentList
                .export(fileContentManager)
                .first, // TODO("Uri)
            signatures = if (param.payload.complete == true) {
                val signaturePayloads = mutableListOf<SignaturePayload>()
                val signatureDocAttachments = mutableListOf<DocAttachment>()
                param.signaturesList?.forEach {
                    val id = UUID.randomUUID().toString()
                    signatureDocAttachments.add(
                        DocAttachment(
                            group = id,
                            fileName = "signature_${it.name}_$id.png"
                        )
                    )
                    signaturePayloads.add(SignaturePayload(id, it.name))
                }
                Pair(signatureDocAttachments, signaturePayloads)
            } else null
        )
        // 2. Sign-off
        return signoffOrSave(param)
            .flatMapError {
                if (it is SCError.NoNetwork) {
                    // No internet connection case
                    taskDAO.insertOfflineTask(
                        newOfflineTask(param)
                    )
                    return@flatMapError Result.Success(
                        SignoffStatus.OfflineCompleted(
                            title = param.title,
                            moduleName = param.moduleName.value,
                            isOverwritten = false
                        )
                    )
                }
                return@flatMapError Result.Error(
                    SCError.SignOffFailed(
                        title = param.title,
                        moduleName = param.moduleName.value,
                        details = it.toString()
                    )
                )
            }
    }

    suspend fun signoff(param: T): Result<SignoffStatus> {
        return overwrite(param) ?: signoffInternal(param)
    }

    /** */
    protected open fun onPayloadModifications(
        mutablePayload: P,
        actionLinks: List<ActionLink>?,
        docAttachments: List<DocAttachment>?,
        signatures: Pair<List<DocAttachment>, List<SignaturePayload>>?
    ) {}

    abstract suspend operator fun invoke(params: T): Result<SignoffStatus>
}
