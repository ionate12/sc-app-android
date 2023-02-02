package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.domain.usecase.action.OfflineTaskInfo
import au.com.safetychampion.util.koinInject

class SignoffChemicalUseCase : BaseSignoffUseCase<ChemicalSignoffParam>() {

    private val repository: IChemicalRepository by koinInject()

    override suspend fun signoffInternal(param: ChemicalSignoffParam): Result<SignoffStatus> {
        return if (param.task.complete) {
            repository.signoff(
                moduleId = param.moduleId,
                taskId = param.taskId,
                body = param.task,
                photos = param.attachments
            )
        } else {
            repository.save(
                moduleId = param.moduleId,
                taskId = param.taskId,
                body = param.task,
                photos = param.attachments
            )
        }
    }
}

data class ChemicalSignoffParam(
    val taskId: String,
    val moduleId: String,
    val task: ChemicalTask,
    val attachments: List<Attachment>
) : OfflineTaskInfo {
    override val id: String
        get() = ""
    override val moduleName: ModuleName = ModuleName.CHEMICAL
    override val title: String
        get() = "1234"
    override val offlineTitle: String
        get() = (if (task.complete) "[SIGN-OFF]" else "[SAVE]") + "$moduleName Sign-off: " + title
}
