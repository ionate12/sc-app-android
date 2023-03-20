package au.com.safetychampion.data.di

import PrepareSignoffActionUseCase
import au.com.safetychampion.data.domain.usecase.action.*
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.auth.*
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetGhsCodeUseCase
import au.com.safetychampion.data.domain.usecase.chemical.GetListChemicalUseCase
import au.com.safetychampion.data.domain.usecase.chemical.PerpareSignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.chemical.SignoffChemicalUseCase
import au.com.safetychampion.data.domain.usecase.crisk.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.contractor.FetchContractorUseCase
import au.com.safetychampion.data.domain.usecase.contractor.GetListContractorUseCase
import au.com.safetychampion.data.domain.usecase.contractor.GetLinkedTaskUseCase
import au.com.safetychampion.data.domain.usecase.crisk.*
import au.com.safetychampion.data.domain.usecase.document.*
import au.com.safetychampion.data.domain.usecase.inspection.GetAvailableListInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.GetInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.NewChildAndStartInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.PrepareSignoffInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.SignoffInspectionUseCase
import au.com.safetychampion.data.domain.usecase.inspection.StartTaskInspectionUseCase
import au.com.safetychampion.data.domain.usecase.hr.FetchHrDetailUseCase
import au.com.safetychampion.data.domain.usecase.hr.GetListHrUseCase
import au.com.safetychampion.data.domain.usecase.hr.GetListLinkedIncidentsUseCase
import au.com.safetychampion.data.domain.usecase.mobileadmin.GetAnnouncementUseCase
import au.com.safetychampion.data.domain.usecase.mobileadmin.GetVersionUseCase
import au.com.safetychampion.data.domain.usecase.pushnotification.*
import au.com.safetychampion.data.domain.usecase.incident.*
import au.com.safetychampion.data.domain.usecase.noticeboard.*
import au.com.safetychampion.data.domain.usecase.reviewplan.*
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

    // Hr

    factoryOf(::FetchHrDetailUseCase)

    factoryOf(::GetListHrUseCase)

    // Document

    factoryOf(::FetchCopySourceUseCase)

    factoryOf(::FetchDocumentUseCase)

    factoryOf(::PrepareSignoffDocumentUseCase)

    factoryOf(::GetListDocumentUseCase)

    // Incident *_*

    factoryOf(::CreateIncidentUseCase)

    factoryOf(::FetchIncidentUseCase)

    factoryOf(::FetchListIncidentUseCase)

    factoryOf(::FetchLocationConfigUseCase)

    factoryOf(::LookUpIncidentUseCase)

    factoryOf(::PrepareIncidentSignoffUseCase)

    // Noticeboard -[ ^^ ] -
    factoryOf(::FetchListNoticeboardBlockUseCase)

    factoryOf(::FetchListDocumentUseCase)

    factoryOf(::FetchListNoticeboardUseCase)

    // contractor
    factoryOf(::FetchContractorUseCase)

    factoryOf(::GetListContractorUseCase)

    factoryOf(::GetLinkedTaskUseCase)

    // Mobile Admin

    factoryOf(::GetAnnouncementUseCase)

    factoryOf(::GetVersionUseCase)

    // Push notification

    factoryOf(::DeleteDeviceTokenUseCase)

    factoryOf(::GetFirebaseTokenUseCase)

    factoryOf(::GetListPushNotificationUseCase)

    factoryOf(::RegisterOrFetchFirebaseTokenUseCase)

    factoryOf(::SetupPushNotificationUseCase)

    factoryOf(::UpdatePushNotificationReadStatusUseCase)

    factoryOf(::GetListLinkedIncidentsUseCase)

    // Signoff

    factoryOf(::FetchListVdocUseCase)

    factoryOf(::FetchNoticeboardFormsUseCase)

    factoryOf(::SubmitNoticeboardFormUseCase)

    // Review_Plan
    factoryOf(::FetchActionsWithReviewPlanIdUseCase)

    factoryOf(::FetchListReviewPlanUseCase)

    factoryOf(::FetchReviewPlanEvidencesUseCase)

    factoryOf(::PrepareReviewPlanSignoffUseCase)

    factoryOf(::SignoffReviewPlanUseCase)

    // Signoff @_@

    factoryOf(::SignoffActionUseCase)

    factoryOf(::SignoffChemicalUseCase)

    factoryOf(::SignoffDocumentUseCase)

    factoryOf(::SignoffCriskUseCase)

    factoryOf(::SignoffDocumentUseCase)

    factoryOf(::SignoffIncidentUseCase)

    factoryOf(::SignoffDocumentUseCase)
}
