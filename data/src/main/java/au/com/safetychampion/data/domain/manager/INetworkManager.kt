package au.com.safetychampion.data.domain.manager

import retrofit2.Retrofit

interface INetworkManager {
    val baseUrl: String
    val retrofit: Retrofit
    suspend fun isNetworkAvailable(): Boolean
}
