package au.com.safetychampion.di

import au.com.safetychampion.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModules = module {
    singleOf(::MainViewModel)
}
