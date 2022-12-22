package au.com.safetychampion.data.di

import au.com.safetychampion.data.di.retrofit.commonModule

val dataModule = commonModule + repositoryModule + useCasesModule
