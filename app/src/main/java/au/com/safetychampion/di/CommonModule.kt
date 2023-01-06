package au.com.safetychampion.di

import au.com.safetychampion.data.util.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.local.AppDataStore
import au.com.safetychampion.util.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val commonModule = module {
    singleOf<IGsonManager>(::GsonManager)

    singleOf<INetworkManager, IGsonManager, ITokenManager>(::NetworkManager)

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

    single<IFileManager> { FileContentManager(androidApplication().contentResolver) }
}
