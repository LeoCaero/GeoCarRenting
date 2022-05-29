package com.example.geocarrenting.API.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id"        ) var Id         : String?           = null,
    @SerializedName("name"       ) var name       : String?           = null,
    @SerializedName("surname"    ) var surname    : String?           = null,
    @SerializedName("userImage"  ) var userImage  : String?           = null,
    @SerializedName("rentedCars" ) var rentedCars : Int?              = null,
    @SerializedName("email"      ) var email      : String?           = null,
    @SerializedName("lastLogin"  ) var lastLogin  : String?           = null,
    @SerializedName("password"   ) var password   : String?           = null,
    @SerializedName("username"   ) var username   : String?           = null,
    @SerializedName("cars"       ) var cars       : ArrayList<Car>? = arrayListOf(),

)