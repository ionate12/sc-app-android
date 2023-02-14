package au.com.safetychampion.data.visitor.domain

import au.com.safetychampion.data.visitor.domain.usecase.ArriveAndUpdateUseCase
import au.com.safetychampion.data.visitor.domain.usecase.LeaveAndUpdateUseCase
import au.com.safetychampion.data.visitor.domain.usecase.SetAutoSignoutUseCase
import au.com.safetychampion.data.visitor.domain.usecase.evidence.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.domain.usecase.evidence.GetLocalEvidenceUseCase
import au.com.safetychampion.data.visitor.domain.usecase.evidence.RetainEvidencesDataUseCase
import au.com.safetychampion.data.visitor.domain.usecase.evidence.UpdateActivitiesUseCase
import au.com.safetychampion.data.visitor.domain.usecase.qr.DecodeQRUseCase
import au.com.safetychampion.data.visitor.domain.usecase.qr.SubmitQRCodeUseCase
import au.com.safetychampion.data.visitor.domain.usecase.site.FetchSiteUseCase
import au.com.safetychampion.data.visitor.domain.usecase.site.UpdateSiteByFormFetchUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val visitorUseCaseModule = module {

    factoryOf(::FetchSiteUseCase)

    factoryOf(::FetchListEvidenceUseCase)

    factoryOf(::DecodeQRUseCase)

    factoryOf(::RetainEvidencesDataUseCase)

    factoryOf(::SubmitQRCodeUseCase)

    factoryOf(::UpdateActivitiesUseCase)

    factoryOf(::FetchEvidenceUseCase)

    factoryOf(::GetLocalEvidenceUseCase)

    factoryOf(::UpdateSiteByFormFetchUseCase)

    factoryOf(::ArriveAndUpdateUseCase)

    factoryOf(::LeaveAndUpdateUseCase)

    factoryOf(::SetAutoSignoutUseCase)
}
