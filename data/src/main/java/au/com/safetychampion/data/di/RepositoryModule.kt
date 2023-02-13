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
    singleOf<IVisitorRemoteRepository>(::VisitorRemoteRepositoryImpl)
    singleOf<IVisitorLocalRepository>(::VisitorLocalRepositoryImpl)

    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }
}
