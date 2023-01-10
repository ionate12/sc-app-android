package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.util.koinInject

abstract class BaseUseCase {
    protected val dispatchers: IDispatchers by koinInject()
}
