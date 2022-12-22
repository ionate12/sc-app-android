package au.com.safetychampion.di

import au.com.safetychampion.data.data.common.TaskAPI
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.di.retrofit.INetworkManager
import org.koin.dsl.module

internal val repositoryModule = module {
    single<TaskAPI> { get<INetworkManager>().retrofit.create(TaskAPI::class.java) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}
