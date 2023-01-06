package au.com.safetychampion.di

import au.com.safetychampion.MainViewModel
import au.com.safetychampion.data.local.BaseAppDataStore
import au.com.safetychampion.local.AppDataStore
import au.com.safetychampion.util.FileContentManager
import au.com.safetychampion.util.IFileManager
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    viewModelOf(::MainViewModel)
    single<BaseAppDataStore> { AppDataStore(androidApplication()) }
    single<IFileManager> { FileContentManager(androidApplication()) }
}
