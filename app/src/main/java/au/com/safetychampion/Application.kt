package au.com.safetychampion

import au.com.safetychampion.data.di.retrofit.commonModule
import au.com.safetychampion.di.appModules
import au.com.safetychampion.di.usecases.useCasesModule
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
                appModules,
                useCasesModule,
                commonModule
            )
        }
        plant(Timber.DebugTree())
    }
}
