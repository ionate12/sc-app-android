package au.com.safetychampion.di

import au.com.safetychampion.MainViewModel
import au.com.safetychampion.data.local.BaseAppDataStore
import au.com.safetychampion.local.AppDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = module {
    singleOf(::MainViewModel)
    single<BaseAppDataStore> { AppDataStore(androidApplication()) }
}
