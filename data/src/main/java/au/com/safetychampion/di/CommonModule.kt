package au.com.safetychampion.data.di.retrofit

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.utils.gsonadapters.BasePLTypeAdapter
import au.com.safetychampion.utils.gsonadapters.TierTypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun getToken() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjVlZmJlYmE0YzZiYWMzMTYxOWUxMWJlNCJ9LCJpYXQiOjE2NzE1OTQ5MjcsImV4cCI6MTY3MTY4MTMyN30.Deq2bJn5mfnKFX4fD6JkBhaU0QvcNNc2qOwgGBvQAYE"
private const val BASE_URL = "https://api.dev.safetychampion.tech"

val commonModule = module {

    singleOf<IGsonManager>(::GsonManager)

    single<INetworkManager> {
        NetworkManager(
            baseUrl = BASE_URL,
            httpClient = OkHttpClient.Builder()
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
            converterFactory = GsonConverterFactory.create(get())
        )
    }
}

interface IGsonManager {
    val gsonBuilder: GsonBuilder
    val gson: Gson
    val cleanGson: Gson
}

internal interface INetworkManager {
    val retrofit: Retrofit
}

private class GsonManager : IGsonManager {
    override val gsonBuilder: GsonBuilder by lazy {
        GsonBuilder()
            .registerTypeHierarchyAdapter(BasePL::class.java, BasePLTypeAdapter())
            .registerTypeAdapter(TierType::class.java, TierTypeConverter())
    }
    override val gson: Gson by lazy { gsonBuilder.create() }
    override val cleanGson by lazy { Gson() }
}

private class NetworkManager(
    private val baseUrl: String,
    private val httpClient: OkHttpClient,
    private val converterFactory: Converter.Factory
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
