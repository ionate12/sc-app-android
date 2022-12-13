package au.com.safetychampion.data.network

import au.com.safetychampion.data.gsonadapter.GsonUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.dev.safetychampion.tech"
    fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(headersInterceptor())
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
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjVlZmJlYmE0YzZiYWMzMTYxOWUxMWJlNCJ9LCJpYXQiOjE2NzA5MTQ1ODUsImV4cCI6MTY3MTAwMDk4NX0.ts4YXtk2BMDclAwwDDae-T-7u7DsFfs0ocg5ymSFRS8"
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
