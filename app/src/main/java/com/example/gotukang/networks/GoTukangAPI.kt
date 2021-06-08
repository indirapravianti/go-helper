package com.example.gotukang.networks

import com.example.gotukang.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoTukangAPI {
    @GET("user/list")
    fun getUserList(): Call<UserList>

    @GET("tukang/list")
    fun getTukangList(): Call<TukangList>

    @GET("tukang/find/username")
    fun getTukangByUsername(@Body findTukangUsernameRequest: FindTukangUsernameRequest): Call<TukangListItem>

    @POST("order/add")
    fun addOrderToTukang(@Body orderRequest: AddOrderRequest): Call<List<String>>
}