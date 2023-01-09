package au.com.safetychampion.util

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.domain.manager.ITokenManager
import au.com.safetychampion.dispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.util.concurrent.TimeUnit

class NetworkManager : INetworkManager {
    private val gsonManager: IGsonManager by koinInject()
    private val tokenManager: ITokenManager by koinInject()
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            Interceptor { chain ->
                val builder = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                // RunBlocking is a must here to getToken. Cancellation will be handled by RequestTimeoutException
                // TODO: Should not use runblocking here. too risky!!
                runBlocking(dispatchers().io) { tokenManager.getToken() }?.let {
                    builder.addHeader("Authorization", it.value)
                }
                val request = builder.build()
                chain.proceed(request)
            }
        )
        .addInterceptor(
            interceptor = HttpLoggingInterceptor()
                .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .build()

    override val baseUrl: String = "https://api.dev.safetychampion.tech"

    override val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonManager.gson))
            .build()
    }

    override suspend fun isNetworkAvailable(): Boolean {
        return withContext(Dispatchers.Default) {
            try {
                val sock = Socket()
                val sockAddress: SocketAddress = InetSocketAddress("8.8.8.8", 53)
                sock.use {
                    it.connect(sockAddress, 5000)
                } // This will block no more than timeoutMs
                true
            } catch (e: IOException) {
                false
            }
        }
    }
}
