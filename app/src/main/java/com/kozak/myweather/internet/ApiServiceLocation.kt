package com.kozak.myweather.internet

import com.kozak.myweather.data.DataFormat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//Getting json array by current location
interface ApiServiceLocation {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("lat") lat: Double,
                              @Query("lon") lon: Double,
                              @Query("units") units: String,
                              @Query("APPID") app_id: String): Call<DataFormat>
}