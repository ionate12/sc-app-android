package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionRepositoryImpl
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.data.api.RestApi
import au.com.safetychampion.data.data.auth.AuthRepository
import au.com.safetychampion.data.data.auth.IAuthRepository
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
import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.data.mobileadmin.IMobileAdminRepository
import au.com.safetychampion.data.data.mobileadmin.MobileAdminRepository
import au.com.safetychampion.data.data.pushnotification.IPushNotificationLocalRepository
import au.com.safetychampion.data.data.pushnotification.IPushNotificationRemoteRepository
import au.com.safetychampion.data.data.pushnotification.PushNotificationLocalRepository
import au.com.safetychampion.data.data.pushnotification.PushNotificationRemoteRepository
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.visitor.data.local.IVisitorLocalRepository
import au.com.safetychampion.data.visitor.data.local.VisitorLocalRepositoryImpl
import au.com.safetychampion.data.visitor.data.remote.IVisitorRemoteRepository
import au.com.safetychampion.data.visitor.data.remote.VisitorRemoteRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf<ITaskRepository> (::TaskRepositoryImpl)
    singleOf<IActionRepository>(::ActionRepositoryImpl)
    singleOf<IBannerRepository>(::BannerRepositoryImpl)
    singleOf<IChemicalRepository>(::ChemicalRepositoryImpl)
    singleOf<ICriskRepository>(::CriskRepositoryImpl)
    singleOf<IMobileAdminRepository>(::MobileAdminRepository)
    singleOf<IPushNotificationRemoteRepository>(::PushNotificationRemoteRepository)
    singleOf<IPushNotificationLocalRepository>(::PushNotificationLocalRepository)
    singleOf<IVisitorRemoteRepository>(::VisitorRemoteRepositoryImpl)
    singleOf<IVisitorLocalRepository>(::VisitorLocalRepositoryImpl)
    singleOf<IDocumentRepository>(::DocumentRepositoryImpl)
    singleOf<IAuthRepository>(::AuthRepository)
    singleOf(::SyncableRepository)

    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }
}
