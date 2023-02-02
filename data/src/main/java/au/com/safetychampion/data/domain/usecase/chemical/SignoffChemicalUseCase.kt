package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.domain.usecase.action.SignatureView
import au.com.safetychampion.data.domain.usecase.action.SignoffParams

class SignoffChemicalUseCase(
    repository: IChemicalRepository
) : BaseSignoffUseCase<ChemicalSignoffParam, ChemicalTask>(
    repository
) {
    override suspend fun invoke(params: ChemicalSignoffParam): Result<SignoffStatus> {
        return signoff(params)
    }
}

data class ChemicalSignoffParam(
    val moduleID: String,
    override val attachmentList: List<Attachment>,
    override val moduleName: ModuleName,
    override val payload: ChemicalTask,
    override val signaturesList: List<SignatureView>?,
    override val title: String
) : SignoffParams()
