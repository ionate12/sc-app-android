package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.data.api.RestApi
import au.com.safetychampion.data.data.banner.BannerRepository
import au.com.safetychampion.data.data.banner.IBannerRepository
import au.com.safetychampion.data.data.chemical.ChemicalRepository
import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.crisk.CriskRepository
import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.data.document.DocumentRepository
import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.data.incident.IncidentRepository
import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository
import au.com.safetychampion.data.data.noticeboard.NoticeboardRepository
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.domain.models.auth.AuthRepository
import au.com.safetychampion.data.domain.models.auth.IAuthRepository
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

    singleOf<IDocumentRepository>(::DocumentRepository)

    singleOf<IIncidentRepository>(::IncidentRepository)

    singleOf<INoticeboardRepository>(::NoticeboardRepository)

    singleOf<ITaskRepository> (::TaskRepository)

    singleOf<IVisitorRemoteRepository>(::VisitorRemoteRepository)

    singleOf<IVisitorLocalRepository>(::VisitorLocalRepository)

    singleOf(::SyncableRepository)

    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }
}
