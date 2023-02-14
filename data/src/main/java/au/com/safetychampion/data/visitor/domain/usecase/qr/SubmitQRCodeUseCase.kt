package au.com.safetychampion.data.visitor.domain.usecase.qr

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.visitor.domain.models.Destination
import au.com.safetychampion.data.visitor.domain.models.VisitorToken
import au.com.safetychampion.data.visitor.domain.usecase.BaseVisitorUseCase
import timber.log.Timber

class SubmitQRCodeUseCase : BaseVisitorUseCase() {
    private val decodeUseCase: DecodeQRUseCase = koinGet()

    /**
     * @param destination a [Destination] would like to go in situation received a null token.
     * @return [Result.Error] if [decodeUseCase] can not decode the QR code, or
     * visitorToken.token = null or any general error from network layer.
     * @see [SCError.InvalidQRCodeRequest]
     * @see DecodeQRUseCase
     */
    suspend operator fun invoke(
        qrCode: String,
        pin: String? = null,
        destination: (() -> Destination)? = null
    ): Result<VisitorToken> {
        return decodeUseCase.invoke(qrCode)?.let { decoded ->
            remoteRepository.token(
                orgId = decoded.first,
                siteId = decoded.second,
                pin = pin
            ).flatMap { visitorToken ->
                if (visitorToken.token == null) {
                    return@flatMap Result.Error(
                        SCError.InvalidQRCodeRequest(des = destination?.invoke())
                    )
                }
                Result.Success(visitorToken)
            }.doOnFailure {
                Timber.tag("d").d(this.toString())
            }
        } ?: Result.Error(SCError.InvalidQRCodeRequest())
    }
}
