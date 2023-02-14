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
import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.domain.manager.INetworkManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf<ITaskRepository> (::TaskRepositoryImpl)
    singleOf<IActionRepository>(::ActionRepositoryImpl)
    singleOf<IBannerRepository>(::BannerRepositoryImpl)
    singleOf<IChemicalRepository>(::ChemicalRepositoryImpl)
    singleOf<ICriskRepository>(::CriskRepositoryImpl)
    singleOf(::SyncableRepository)

    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }
}
