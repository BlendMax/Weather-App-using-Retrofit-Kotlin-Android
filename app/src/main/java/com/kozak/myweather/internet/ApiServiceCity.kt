package com.kozak.myweather.internet

import com.kozak.myweather.data.DataFormat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//Getting json array by city
interface ApiServiceCity {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("q") q: String,
                              @Query("units") units: String,
                              @Query("APPID") app_id: String): Call<DataFormat>

}