package au.com.safetychampion.util

import au.com.safetychampion.data.util.ITokenManager
import au.com.safetychampion.data.util.dispatchers
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

private const val BASE_URL = "https://api.dev.safetychampion.tech"

internal interface INetworkManager {
    val retrofit: Retrofit
}

class NetworkManager() : INetworkManager {
    companion object {
        suspend fun internetAvailable(): Boolean {
            return withContext(Dispatchers.Default) {
                try {
                    val sock = Socket()
                    val sockAddress: SocketAddress = InetSocketAddress("8.8.8.8", 53)
                    sock.use {
                        it.connect(sockAddress, 3000)
                    } // This will block no more than timeoutMs
                    true
                } catch (e: IOException) {
                    false
                }
            }
        }
    }

    private val baseUrl: String = BASE_URL
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
    private val gsonManager by koinInject<IGsonManager>()
    private val tokenManager by koinInject<ITokenManager>()

    override val retrofit: Retrofit by lazy {

        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonManager.gson))
            .build()
    }
}
