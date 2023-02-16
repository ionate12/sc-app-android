package au.com.safetychampion.data.domain.manager

import au.com.safetychampion.data.data.local.BaseAppDataStore
import au.com.safetychampion.data.data.local.StoreKey
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.auth.LoginUser
import au.com.safetychampion.data.domain.models.config.PermissionType
import au.com.safetychampion.data.domain.models.config.module.BaseConfig
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface IUserInfoManager {
    suspend fun getUser(): LoginUser?
    suspend fun <T : BaseConfig> getConfig(type: ModuleType): T?
    suspend fun storeUser(nUser: LoginUser)
    suspend fun hasPermission(type: ModuleType, permissionType: PermissionType): Boolean
    suspend fun hasMorphPermission(type: ModuleType, permissionType: PermissionType): Boolean
    suspend fun moduleTitle(type: ModuleType): String
}
class UserInfoManager : IUserInfoManager {
    // Cache Data
    private var loginUser: LoginUser? = null
    private var config: Map<ModuleType, BaseConfig>? = null
    private var morphStatus: String? = null // TODO: manage morph

    // Deps
    private val appDataStore: BaseAppDataStore by koinInject()
    private val dispatchers: IDispatchers by koinInject()

    private var initJob: Job? = null

    init {
        initJob = initialize()
    }

    private suspend fun <T : Any?> didInit(block: suspend () -> T): T {
        initJob?.join()
        return block.invoke()
    }

    private fun initialize(): Job = CoroutineScope(dispatchers.io).launch {
        loginUser = appDataStore.get(StoreKey.UserInfo)
        setupConfig(loginUser)
    }

    private fun setupConfig(loginUser: LoginUser?) {
        config = loginUser?.configuration?.let { BaseConfig.create(it) }
    }

    override suspend fun getUser(): LoginUser? = didInit { loginUser }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T : BaseConfig> getConfig(type: ModuleType): T? = didInit {
        config?.get(type) as? T
    }

    override suspend fun storeUser(nUser: LoginUser) = didInit {
        appDataStore.store(StoreKey.UserInfo, nUser)
        loginUser = nUser
        setupConfig(loginUser)
    }

    override suspend fun hasPermission(
        type: ModuleType,
        permissionType: PermissionType
    ): Boolean {
        return getConfig<BaseConfig>(type)?.permissions?.contains(permissionType) == true
    }

    override suspend fun hasMorphPermission(
        type: ModuleType,
        permissionType: PermissionType
    ): Boolean {
        return getConfig<BaseConfig>(type)?.morphPermissions?.contains(permissionType) == true
    }

    override suspend fun moduleTitle(type: ModuleType): String {
        return getConfig<BaseConfig>(type)?.title ?: type.value
    }
}
