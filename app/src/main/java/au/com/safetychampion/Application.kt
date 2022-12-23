package au.com.safetychampion

import au.com.safetychampion.data.di.dataModule
import au.com.safetychampion.data.util.ITokenManager
import au.com.safetychampion.di.appModules
import au.com.safetychampion.util.koinGet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.Forest.plant

class Application : android.app.Application() {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                appModules + dataModule
            )
        }
        plant(Timber.DebugTree())
    }
}
