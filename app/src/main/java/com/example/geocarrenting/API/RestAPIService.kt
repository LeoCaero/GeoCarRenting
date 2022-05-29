package com.example.geocarrenting.API

import com.example.geocarrenting.API.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestAPIService {

    fun createuser(userInfo: User, onResult: (User?) -> Unit){
        val retrofit = APIService.ServiceBuilder.buildService(APIService::class.java)
        retrofit.postUser(userInfo).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<User>, response: Response<User>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}