package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionRepositoryImpl
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.data.api.RestApi
import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.domain.manager.INetworkManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf<ITaskRepository> (::TaskRepositoryImpl)
    singleOf<IActionRepository>(::ActionRepositoryImpl)
    single<RestApi> { get<INetworkManager>().retrofit.create(RestApi::class.java) }
}
