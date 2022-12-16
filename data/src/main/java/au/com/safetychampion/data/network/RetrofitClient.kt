package au.com.safetychampion.data.network

import au.com.safetychampion.data.gsonadapter.GsonUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level

object RetrofitClient {
    private const val BASE_URL = "https://api.dev.safetychampion.tech"
    fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(headersInterceptor())
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()

        val gson = GsonUtil.gsonBuilder
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun token(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjVlZmJlYmE0YzZiYWMzMTYxOWUxMWJlNCJ9LCJpYXQiOjE2NzEwOTM2MTAsImV4cCI6MTY3MTE4MDAxMH0.kCGdGafGuGnpMXjcbM8Iz84SWEyk2uMLxezs4I88x5o"
    }

    private fun headersInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", token())
            .build()
        chain.proceed(request)
    }
}
