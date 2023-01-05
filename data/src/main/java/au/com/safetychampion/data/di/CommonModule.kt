package au.com.safetychampion.data.di

import au.com.safetychampion.data.util.IDispatchers
import au.com.safetychampion.data.util.ITokenManager
import au.com.safetychampion.data.util.TokenManager
import au.com.safetychampion.util.GsonManager
import au.com.safetychampion.util.IGsonManager
import au.com.safetychampion.util.INetworkManager
import au.com.safetychampion.util.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val commonModule = module {

    singleOf<IGsonManager>(::GsonManager)

    singleOf<INetworkManager> (::NetworkManager)
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
}
