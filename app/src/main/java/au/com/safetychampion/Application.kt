package au.com.safetychampion

import au.com.safetychampion.data.di.dataModule
import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.di.appModules
import au.com.safetychampion.di.commonModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
                appModules + dataModule + commonModule
            )
        }
        plant(Timber.DebugTree())
    }
}

fun dispatchers(): IDispatchers = koinGet()
