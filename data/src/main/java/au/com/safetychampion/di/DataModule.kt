package au.com.safetychampion.di

import au.com.safetychampion.data.di.retrofit.commonModule

val dataModule = commonModule + repositoryModule + useCasesModule
