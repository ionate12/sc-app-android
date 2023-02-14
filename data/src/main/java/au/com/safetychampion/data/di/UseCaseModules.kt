package au.com.safetychampion.data.di

import PrepareSignoffActionUseCase
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetGhsCodeUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetListChemicalUseCase
import au.com.safetychampion.data.domain.usecase.chemical.PerpareSignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.chemical.SignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.crisk.* // ktlint-disable no-wildcard-imports
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCasesModule = module {

    // Active tasks

    factoryOf(::AssignManyTasksStatusItemUseCase)

    factoryOf(::AssignTaskStatusItemUseCase)

    factoryOf(::GetAllActiveTaskUseCase)

    factoryOf(::AssignTaskUseCase)

    factoryOf(::UnAssignTaskUseCase)

    // Action

    factoryOf(::CreateActionUseCase)

    factoryOf(::GetListActionUseCase)

    factoryOf(::PrepareSignoffActionUseCase)

    factoryOf(::EditActionUseCase)

    factoryOf(::CreatePendingActionUseCase)

    factoryOf(::CreateMultiPendingActionsUseCase)

    // Chemicals

    factoryOf(::PerpareSignoffChemicalUseCase)

    factoryOf(::GetListChemicalUseCase)

    factoryOf(::GetGhsCodeUseCase)

    // Banner

    factoryOf(::GetListBannerUseCase)

    // Crisk

    factoryOf(::GetListCriskUseCase)

    factoryOf(::GetListHrLookupItemUseCase)

    factoryOf(::GetListContractorLookupUseCase)

    factoryOf(::FetchCriskUseCase)

    factoryOf(::PrepareSignoffCriskUseCase)

    factoryOf(::GetCriskTaskEvidenceUseCase)

    factoryOf(::ArchiveCriskUseCase)

    // Signoff

    factoryOf(::SignoffChemicalUseCase)

    factoryOf(::SignoffActionUseCase)

    factoryOf(::SignoffCriskUseCase)
}
