package au.com.safetychampion.data.di

import PrepareSignoffActionUseCase
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.auth.GetWhoAmIUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserLoginUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserLogoutUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserMorphUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserMultiLoginUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserUnMorphUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserVerifyMfaUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetGhsCodeUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetListChemicalUseCase
import au.com.safetychampion.data.domain.usecase.chemical.PerpareSignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.chemical.SignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.crisk.*
import au.com.safetychampion.data.domain.usecase.document.*
import au.com.safetychampion.data.domain.usecase.inspection.GetAvailableListInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.GetInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.NewChildAndStartInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.PrepareSignoffInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.SignoffInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.StartTaskInspectionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCasesModule = module {

    // Auth

    factoryOf(::UserLoginUseCase)
    factoryOf(::UserMultiLoginUseCase)
    factoryOf(::UserVerifyMfaUseCase)
    factoryOf(::UserMorphUseCase)
    factoryOf(::UserUnMorphUseCase)
    factoryOf(::GetWhoAmIUseCase)
    factoryOf(::UserLogoutUseCase)

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

    // Inspection

    factoryOf(::GetAvailableListInspectionUseCase)
    factoryOf(::NewChildAndStartInspectionUseCase)
    factoryOf(::PrepareSignoffInspectionUseCase)
    factoryOf(::SignoffInspectionUseCase)
    factoryOf(::StartTaskInspectionUseCase)
    factoryOf(::GetInspectionUseCase)

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

    // Document

    factoryOf(::FetchCopySourceUseCase)

    factoryOf(::FetchDocumentUseCase)

    factoryOf(::PrepareDocumentSignoffUseCase)

    factoryOf(::SignoffDocumentUseCase)

    factoryOf(::FetchListDocumentUseCase)

    // Signoff

    factoryOf(::SignoffChemicalUseCase)

    factoryOf(::SignoffActionUseCase)

    factoryOf(::SignoffCriskUseCase)
}
