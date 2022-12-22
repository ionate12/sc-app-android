package au.com.safetychampion.data.di.retrofit

import au.com.safetychampion.util.GsonManager
import au.com.safetychampion.util.IGsonManager
import au.com.safetychampion.util.INetworkManager
import au.com.safetychampion.util.NetworkManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {

    singleOf<IGsonManager>(::GsonManager)

    singleOf<INetworkManager> (::NetworkManager)
}
