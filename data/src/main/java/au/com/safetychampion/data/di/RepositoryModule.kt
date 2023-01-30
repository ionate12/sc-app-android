package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionAPI
import au.com.safetychampion.data.data.action.ActionRepositoryImpl
import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.data.api.RestAPI
import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.data.common.TaskAPI
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.domain.manager.INetworkManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    single<TaskAPI> { get<INetworkManager>().retrofit.create(TaskAPI::class.java) }
    singleOf<ITaskRepository> (::TaskRepositoryImpl)

    single<ActionAPI> { get<INetworkManager>().retrofit.create(ActionAPI::class.java) }
    singleOf<IActionRepository>(::ActionRepositoryImpl)
    single<RestAPI> { get<INetworkManager>().retrofit.create(RestAPI::class.java) }
}
