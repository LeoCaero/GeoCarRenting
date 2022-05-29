package com.example.geocarrenting.API

import com.example.geocarrenting.API.model.Car
import com.example.geocarrenting.API.model.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIService {

    @GET()
    suspend fun getCars(@Url url: String): Response<List<Car>>

    @GET()
    suspend fun getCarsByModel(@Url url: String): Response<Car>

    @GET()
    suspend fun getLoginUser(@Url url: String): Response<User>

    @GET()
    suspend fun getUserByEmail(@Url url: String): Response<User>

    @GET("/users/login/")
    suspend fun getUserLogin(@Url url: String): Response<User>

    @PUT("/users/{email}/{model}")
    suspend fun addCarToUser(@Path("email") email: String, @Path("model") model: String): Response<User>

    @POST("/users")
    fun postUser(@Body user: User): Call<User>

    object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://x46vog.deta.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun<T> buildService(service: Class<T>): T{
            return retrofit.create(service)
        }
    }

}