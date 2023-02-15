package au.com.safetychampion.data.di

import au.com.safetychampion.data.visitor.domain.visitorUseCaseModule

val dataModule = dbModule + repositoryModule + useCasesModule + visitorUseCaseModule
