package com.example.geocarrenting.API.model

import com.google.gson.annotations.SerializedName

data class Car (
        @SerializedName("_id"                       ) var Id                   : String? = null,
        @SerializedName("make"                      ) var make                 : String? = null,
        @SerializedName("model"                     ) var model                : String? = null,
        @SerializedName("generation"                ) var generation           : String? = null,
        @SerializedName("year_from"                 ) var yearFrom             : Int?    = null,
        @SerializedName("year_to"                   ) var yearTo               : Int?    = null,
        @SerializedName("series"                    ) var series               : String? = null,
        @SerializedName("body_type"                 ) var bodyType             : String? = null,
        @SerializedName("number_of_seats"           ) var numberOfSeats        : Int?    = null,
        @SerializedName("length_mm"                 ) var lengthMm             : Int?    = null,
        @SerializedName("width_mm"                  ) var widthMm              : Int?    = null,
        @SerializedName("height_mm"                 ) var heightMm             : Int?    = null,
        @SerializedName("number_of_cylinders"       ) var numberOfCylinders    : Int?    = null,
        @SerializedName("engine_type"               ) var engineType           : String? = null,
        @SerializedName("engine_hp"                 ) var engineHp             : Int?    = null,
        @SerializedName("drive_wheels"              ) var driveWheels          : String? = null,
        @SerializedName("number_of_gears"           ) var numberOfGears        : Int?    = null,
        @SerializedName("transmission"              ) var transmission         : String? = null,
        @SerializedName("fuel_tank_capacity_l"      ) var fuelTankCapacityL    : Int?    = null,
        @SerializedName("max_speed_km_per_h"        ) var maxSpeedKmPerH       : Int?    = null,
        @SerializedName("fuel_grade"                ) var fuelGrade            : Int?    = null,
        @SerializedName("car_image"                 ) var carImage             : String? = null,
        @SerializedName("car_price"                 ) var carPrice             : Int?    = null,
        @SerializedName("car_rating"                ) var carRating            : Float?  = null,
)
