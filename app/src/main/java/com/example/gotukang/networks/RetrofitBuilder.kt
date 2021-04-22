package com.example.gotukang.networks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://go-tukang.herokuapp.com/")
            .build()
    }

    fun getService(): GoTukangAPI = getRetrofit().create(GoTukangAPI::class.java)
}