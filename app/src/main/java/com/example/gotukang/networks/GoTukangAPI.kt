package com.example.gotukang.networks

import com.example.gotukang.models.AddOrderRequest
import com.example.gotukang.models.TukangList
import com.example.gotukang.models.UserList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoTukangAPI {
    @GET("user/list")
    fun getUserList(): Call<UserList>

    @GET("tukang/list")
    fun getTukangList(): Call<TukangList>

    @POST("order/add")
    fun addOrderToTukang(@Body orderRequest: AddOrderRequest): Call<List<String>>
}