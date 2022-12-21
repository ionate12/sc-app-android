package au.com.safetychampion.di.usecases

import au.com.safetychampion.data.data.common.TaskAPI
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.di.retrofit.IGson
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.utils.getKoinInstance
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val useCasesModule = module {
    /**
     * UseCases
     */

    singleOf(::AssignManyTasksStatusItemUseCase)

    singleOf(::AssignTaskStatusItemUseCase)

    singleOf(::GetAllActiveTaskUseCase)

    /**
     * Repositories
     */

    single<TaskRepository> { TaskRepositoryImpl(retrofitClient.create(TaskAPI::class.java)) }
}

private const val BASE_URL = "https://api.dev.safetychampion.tech"

private val retrofitClient by lazy {
    val gson = getKoinInstance<IGson>().gson
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClients())
        .addConverterFactory(converters(gson))
        .build()
}

private fun converters(gson: Gson) = GsonConverterFactory.create(gson)

private fun httpClients(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(headersInterceptor())
        .addInterceptor(loggerInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .build()
}

private fun headersInterceptor() = Interceptor { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", getToken())
        .build()
    chain.proceed(request)
}

private fun loggerInterceptor() = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

private fun getToken() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjVlZmJlYmE0YzZiYWMzMTYxOWUxMWJlNCJ9LCJpYXQiOjE2NzE1OTQ5MjcsImV4cCI6MTY3MTY4MTMyN30.Deq2bJn5mfnKFX4fD6JkBhaU0QvcNNc2qOwgGBvQAYE"
