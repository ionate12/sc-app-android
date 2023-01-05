package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.data.action.ActionRepositoryImpl
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.network.ActionAPI
import au.com.safetychampion.data.network.TaskAPI
import au.com.safetychampion.util.INetworkManager
import org.koin.dsl.module

internal val repositoryModule = module {
    single<TaskAPI> { get<INetworkManager>().retrofit.create(TaskAPI::class.java) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }

    single<ActionAPI> { get<INetworkManager>().retrofit.create(ActionAPI::class.java) }
    single<ActionRepository> { ActionRepositoryImpl(get()) }
}
