package au.com.safetychampion.di

import android.content.Context
import androidx.room.Room
import au.com.safetychampion.data.data.common.FakeDAO
import au.com.safetychampion.data.data.common.FakeMasterDAO
import au.com.safetychampion.data.data.common.MasterDAO
import au.com.safetychampion.data.data.common.TaskDAO
import au.com.safetychampion.data.data.local.BaseAppDataStore
import au.com.safetychampion.data.data.local.room.AppDatabase
import au.com.safetychampion.data.domain.manager.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.local.AppDataStore
import au.com.safetychampion.util.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val commonModule = module {
    singleOf<IGsonManager>(::GsonManager)

    singleOf<INetworkManager>(::NetworkManager)

    singleOf<ITokenManager> (::TokenManager)

    single<IDispatchers> {
        object : IDispatchers {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
        }
    }

    single<BaseAppDataStore> { AppDataStore(androidApplication()) }

    single<IFileManager> { FileContentManager(androidApplication().contentResolver, androidApplication().getExternalFilesDir("")?.path ?: "") }

    singleOf<IOfflineConverter> (::OfflineTaskManager)

    singleOf<TaskDAO>(::FakeDAO)

    singleOf<MasterDAO>(::FakeMasterDAO)

    single<AppDatabase> { initRoomDB(androidApplication()) }
}

private fun initRoomDB(appContext: Context) =
    Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.DB_NAME)
        .fallbackToDestructiveMigration()
        .build()
