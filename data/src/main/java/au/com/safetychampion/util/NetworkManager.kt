package au.com.safetychampion.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun getToken() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjVlZmJlYmE0YzZiYWMzMTYxOWUxMWJlNCJ9LCJpYXQiOjE2NzE2OTQzODMsImV4cCI6MTY3MTc4MDc4M30.GAjUJ9ZnVvES2mYoGUobZlpFnUAahlX3oQ7Qm3eaJ-M"
private const val BASE_URL = "https://api.dev.safetychampion.tech"

internal interface INetworkManager {
    val retrofit: Retrofit
}

internal class NetworkManager(
    private val baseUrl: String = BASE_URL,
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", getToken())
                    .build()
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
        .build(),
    private val converterFactory: GsonConverterFactory = GsonConverterFactory.create(koinGet<IGsonManager>().gson)
) : INetworkManager {

    override val retrofit: Retrofit by lazy { getRetrofitInstance() }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}
