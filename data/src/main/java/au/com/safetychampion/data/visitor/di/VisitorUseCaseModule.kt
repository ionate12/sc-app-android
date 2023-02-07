package au.com.safetychampion.data.visitor.domain

import au.com.safetychampion.data.visitor.domain.usecase.FetchSiteUseCase
import au.com.safetychampion.data.visitor.domain.usecase.FetchVisitorEvidenceUseCase
import au.com.safetychampion.data.visitor.domain.usecase.GetDestinationFromQRCodeUseCase
import au.com.safetychampion.data.visitor.domain.usecase.internal.DecodeQRUseCase
import au.com.safetychampion.data.visitor.domain.usecase.internal.RetainEvidencesUseCase
import au.com.safetychampion.data.visitor.domain.usecase.internal.SubmitQRCodeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val visitorUseCaseModule = module {
    // Public use cases, exposes to app module.

    factoryOf(::FetchSiteUseCase)

    factoryOf(::FetchVisitorEvidenceUseCase)

    factoryOf(::GetDestinationFromQRCodeUseCase)

    // Internal use cases.
    factoryOf(::DecodeQRUseCase)

    factoryOf(::RetainEvidencesUseCase)

    factoryOf(::SubmitQRCodeUseCase)

    factoryOf(::SubmitQRCodeUseCase)
}
