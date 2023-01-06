package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.action.ActionAPI
import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.data.action.ActionRepositoryImpl
import au.com.safetychampion.data.data.common.TaskAPI
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.util.IFileManager
import au.com.safetychampion.data.util.INetworkManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    single<TaskAPI> { get<INetworkManager>().retrofit.create(TaskAPI::class.java) }
    singleOf<TaskRepository, TaskAPI> (::TaskRepositoryImpl)

    single<ActionAPI> { get<INetworkManager>().retrofit.create(ActionAPI::class.java) }
    singleOf<ActionRepository, ActionAPI, IFileManager>(::ActionRepositoryImpl)
}
