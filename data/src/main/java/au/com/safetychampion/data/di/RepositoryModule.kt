package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionRepositoryImpl
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.data.api.RestApi
import au.com.safetychampion.data.data.banner.BannerRepositoryImpl
import au.com.safetychampion.data.data.banner.IBannerRepository
import au.com.safetychampion.data.data.chemical.ChemicalRepositoryImpl
import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.data.crisk.CriskRepositoryImpl
import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.data.document.DocumentRepositoryImpl
import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.data.incident.IncidentRepositoryImpl
import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.data.noticeboard.INoticeboardRepository
import au.com.safetychampion.data.data.noticeboard.NoticeboardRepositoryImpl
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.visitor.data.local.IVisitorLocalRepository
import au.com.safetychampion.data.visitor.data.local.VisitorLocalRepositoryImpl
import au.com.safetychampion.data.visitor.data.remote.IVisitorRemoteRepository
import au.com.safetychampion.data.visitor.data.remote.VisitorRemoteRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf<IActionRepository>(::ActionRepositoryImpl)

    singleOf<IBannerRepository>(::BannerRepositoryImpl)

    singleOf<IChemicalRepository>(::ChemicalRepositoryImpl)

    singleOf<ICriskRepository>(::CriskRepositoryImpl)

    singleOf<IDocumentRepository>(::DocumentRepositoryImpl)

    singleOf<IIncidentRepository>(::IncidentRepositoryImpl)

    singleOf<INoticeboardRepository>(::NoticeboardRepositoryImpl)

    singleOf<ITaskRepository> (::TaskRepositoryImpl)

    singleOf<IVisitorRemoteRepository>(::VisitorRemoteRepositoryImpl)

    singleOf<IVisitorLocalRepository>(::VisitorLocalRepositoryImpl)

    singleOf(::SyncableRepository)

    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }
}
