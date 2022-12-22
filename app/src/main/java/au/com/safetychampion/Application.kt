package au.com.safetychampion

import au.com.safetychampion.di.appModules
import au.com.safetychampion.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.Forest.plant

class Application : android.app.Application() {
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
