package au.com.safetychampion.data.visitor.domain.usecase.internal

import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.visitor.data.local.IVisitorLocalRepository
import au.com.safetychampion.data.visitor.data.remote.IVisitorRemoteRepository
import au.com.safetychampion.util.koinInject

open class BaseVisitorUseCase : BaseUseCase() {
    internal val localRepository: IVisitorLocalRepository by koinInject()
    internal val remoteRepository: IVisitorRemoteRepository by koinInject()
}
