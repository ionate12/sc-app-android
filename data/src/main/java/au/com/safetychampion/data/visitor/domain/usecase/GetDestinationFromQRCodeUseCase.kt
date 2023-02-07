package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.domain.models.Destination
import au.com.safetychampion.data.visitor.domain.usecase.internal.BaseVisitorUseCase
import au.com.safetychampion.data.visitor.domain.usecase.internal.SubmitQRCodeUseCase
import au.com.safetychampion.util.koinInject

class GetDestinationFromQRCodeUseCase() : BaseVisitorUseCase() {

    private val fetchSiteUseCase: FetchSiteUseCase by koinInject()

    private val submitQRUseCase: SubmitQRCodeUseCase by koinInject()

    suspend operator fun invoke(qrCode: String): Result<Destination> {
        return submitQRUseCase.invoke(
            qrCode = qrCode,
            destination = { Destination.PinCode(qrCode) }
        )
            .flatMap { token ->
                fetchSiteUseCase.invoke(token.token!!) // non null, already handled in submitQR
                    .flatMap {
                        Result.Success(
                            Destination.VisitorWizard(
                                data = it,
                                token = token.token
                            )
                        )
                    }
            }
            .flatMapError {
                when (it) {
                    is SCError.NoNetwork, is SCError.InvalidQRCodeRequest -> Result.Error(it)
                    else -> Result.Error(SCError.InvalidQRCodeRequest())
                }
            }
    }
}
