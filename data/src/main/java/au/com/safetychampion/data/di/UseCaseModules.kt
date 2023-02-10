package au.com.safetychampion.data.di

import GetActionSignOffDetailsUseCase
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetChemicalSignoffDetailUseCase
import au.com.safetychampion.data.domain.usecase.chemical.RefreshChemicalListUseCase
import au.com.safetychampion.data.domain.usecase.chemical.RefreshGHSCodeUseCase
import au.com.safetychampion.data.domain.usecase.chemical.SignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.contractor.FetchContractorUseCase
import au.com.safetychampion.data.domain.usecase.contractor.GetListContractorUseCase
import au.com.safetychampion.data.domain.usecase.contractor.GetLinkedTaskUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCasesModule = module {

    factoryOf(::AssignManyTasksStatusItemUseCase)

    factoryOf(::AssignTaskStatusItemUseCase)

    factoryOf(::GetAllActiveTaskUseCase)

    factoryOf(::AssignTaskUseCase)

    factoryOf(::UnAssignTaskUseCase)

    factoryOf(::CreateActionUseCase)

    factoryOf(::GetListActionUseCase)

    factoryOf(::GetActionSignOffDetailsUseCase)

    factoryOf(::EditActionUseCase)

    factoryOf(::CreatePendingActionUseCase)

    factoryOf(::GetChemicalSignoffDetailUseCase)

    factoryOf(::RefreshChemicalListUseCase)

    factoryOf(::RefreshGHSCodeUseCase)

    factoryOf(::GetListBannerUseCase)

    // contractor
    factoryOf(::FetchContractorUseCase)
    factoryOf(::GetListContractorUseCase)
    factoryOf(::GetLinkedTaskUseCase)

    // Signoff

    factoryOf(::SignoffChemicalUseCase)
    factoryOf(::SignoffActionUseCase)
}
