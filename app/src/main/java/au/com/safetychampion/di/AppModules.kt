package au.com.safetychampion.di

import au.com.safetychampion.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    viewModelOf(::MainViewModel)
}
