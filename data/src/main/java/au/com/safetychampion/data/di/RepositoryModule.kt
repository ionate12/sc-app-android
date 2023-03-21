package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.data.api.RestApi
import au.com.safetychampion.data.data.auth.AuthRepository
import au.com.safetychampion.data.data.auth.IAuthRepository
import au.com.safetychampion.data.data.banner.BannerRepository
import au.com.safetychampion.data.data.banner.IBannerRepository
import au.com.safetychampion.data.data.chemical.ChemicalRepository
import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.contractor.ContractorRepositoryImpl
import au.com.safetychampion.data.data.contractor.IContractorRepository
import au.com.safetychampion.data.data.crisk.CriskRepository
import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.data.document.DocumentRepository
import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.data.hr.HrRepositoryImpl
import au.com.safetychampion.data.data.hr.IHrRepository
import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.data.incident.IncidentRepository
import au.com.safetychampion.data.data.inspection.IInspectionRepository
import au.com.safetychampion.data.data.inspection.InspectionRepository
import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.data.mobileadmin.IMobileAdminRepository
import au.com.safetychampion.data.data.mobileadmin.MobileAdminRepository
import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository
import au.com.safetychampion.data.data.noticeboard.NoticeboardRepository
import au.com.safetychampion.data.data.reviewplan.IReviewPlanRepository
import au.com.safetychampion.data.data.reviewplan.ReviewPlanRepository
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.visitor.data.local.IVisitorLocalRepository
import au.com.safetychampion.data.visitor.data.local.VisitorLocalRepository
import au.com.safetychampion.data.visitor.data.remote.IVisitorRemoteRepository
import au.com.safetychampion.data.visitor.data.remote.VisitorRemoteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf<IAuthRepository>(::AuthRepository)

    singleOf<IActionRepository>(::ActionRepository)

    singleOf<IBannerRepository>(::BannerRepository)

    singleOf<IChemicalRepository>(::ChemicalRepository)

    singleOf<ICriskRepository>(::CriskRepository)

    singleOf<IContractorRepository>(::ContractorRepositoryImpl)

    singleOf<IDocumentRepository>(::DocumentRepository)

    singleOf<IIncidentRepository>(::IncidentRepository)

    singleOf<INoticeboardRepository>(::NoticeboardRepository)

    singleOf<IReviewPlanRepository>(::ReviewPlanRepository)

    singleOf<ITaskRepository> (::TaskRepository)

    singleOf<IVisitorRemoteRepository>(::VisitorRemoteRepository)

    singleOf<IVisitorLocalRepository>(::VisitorLocalRepository)

    singleOf<IHrRepository>(::HrRepositoryImpl)

    singleOf<IInspectionRepository>(::InspectionRepository)

    singleOf<IMobileAdminRepository>(::MobileAdminRepository)

    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }

    singleOf(::SyncableRepository)
}
