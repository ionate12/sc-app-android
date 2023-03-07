package au.com.safetychampion.data.domain.manager

import au.com.safetychampion.data.domain.uncategory.AppToken
import kotlin.reflect.KClass

interface ITokenManager {
    suspend fun getToken(): AppToken?
    suspend fun updateToken(token: AppToken)
    suspend fun clearTokens()
    suspend fun deleteToken(type: KClass<AppToken>)
}
