package com.example.reliatest.api

import com.example.reliatest.constant.Constant
import com.example.reliatest.constant.Constant.ITEM_PER_PAGE
import com.example.reliatest.model.*
import com.example.reliatest.param.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("users-login")
    suspend fun loginDemo(@Body param: LoginDemoParam): Response<ObjectResponse<User>>

    @GET("feed")
    suspend fun getFeeds(
        @Query("page") page: Int = 1,
        @Query("current_per_page") perPage: Int = ITEM_PER_PAGE
    ): Response<ListResponse<Feed>>

    @POST(Constant.EndPoint.REGISTER)
    suspend fun register(@Body param: RegisterParam): Response<RegisterResponse>

    @POST(Constant.EndPoint.AUTH_LOGIN)
    suspend fun login(@Body param: LoginParam): Response<LoginResponse>

    @GET(Constant.EndPoint.ITEMS)
    suspend fun getProducts(): Response<ArrayList<Product>>

    @POST(Constant.EndPoint.ITEM + Constant.EndPoint.SEARCH)
    suspend fun searchProduct(@Body param: SearchProductParam): Response<Product>

    @POST(Constant.EndPoint.ITEM + Constant.EndPoint.ADD)
    suspend fun addProduct(@Body param: AddProductParam): Response<Product>
}