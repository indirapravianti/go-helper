package com.example.gotukang.networks

import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://go-tukang.herokuapp.com/")
            .client(httpClientBuilder)
            .build()
    }

    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val contentType = Interceptor {
        val originalRequest = it.request()
        val authenticationRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .build()

        it.proceed(authenticationRequest)
    }

    private val httpClientBuilder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0,5, TimeUnit.MINUTES))
        .protocols(listOf(Protocol.HTTP_1_1))
        .addInterceptor(contentType)
        .addInterceptor(logging).build()

    fun getService(): GoTukangAPI = getRetrofit().create(GoTukangAPI::class.java)
}