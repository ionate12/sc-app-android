package au.com.safetychampion.data.di

import au.com.safetychampion.data.domain.manager.IUserInfoManager
import au.com.safetychampion.data.domain.manager.UserInfoManager
import au.com.safetychampion.data.visitor.domain.visitorUseCaseModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = dbModule + repositoryModule + useCasesModule + visitorUseCaseModule + module {
    singleOf<IUserInfoManager>(::UserInfoManager)
}
