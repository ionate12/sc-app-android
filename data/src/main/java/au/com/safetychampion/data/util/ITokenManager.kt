package au.com.safetychampion.data.util

import au.com.safetychampion.data.domain.uncategory.AppToken

interface ITokenManager {
    suspend fun getToken(): AppToken?
    suspend fun updateToken(token: AppToken)
}
