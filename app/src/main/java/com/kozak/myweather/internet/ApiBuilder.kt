package com.kozak.myweather.internet

import android.util.Log
import com.kozak.myweather.data.DataFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiBuilder() {
    var city: String? = null
    var lat:Double? = null
    var lon:Double? = null

    //The function with which we receive data on the temperature in the city
    fun getCurrentDataCity(callback: ApiCallBack) {
        val cityName: String = city.toString()
         val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

         val service = retrofitBuilder.create(ApiServiceCity::class.java)
         val call = service.getCurrentWeatherData(cityName, units,  AppId)
         call.enqueue(object : Callback<DataFormat> {
             override fun onResponse(call: Call<DataFormat>, response: Response<DataFormat>) {
                 val responseBody = response.body()!!
                 if (response.isSuccessful) callback.onSuccess(responseBody.main.temp)

             }

             override fun onFailure(call: Call<DataFormat>, t: Throwable) {
                 Log.e("Error", t.message.toString())
             }
         })
    }

    //The function with which we receive temperature data at the current location
    fun getCurrentDataLocation(callback: ApiCallBack) {
        val latitude: Double = lat!!.toDouble()
        val longitude: Double = lon!!.toDouble()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofitBuilder.create(ApiServiceLocation::class.java)
        val call = service.getCurrentWeatherData(latitude, longitude, units,  AppId)
        call.enqueue(object : Callback<DataFormat> {
            override fun onResponse(call: Call<DataFormat>, response: Response<DataFormat>) {
                val responseBody = response.body()!!
                if (response.isSuccessful) callback.onSuccess(responseBody.main.temp)

            }

            override fun onFailure(call: Call<DataFormat>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }


    companion object {
         const val BaseUrl = "http://api.openweathermap.org/"
         const val AppId = "c8d07a95aed89968c871bf473551d0e1"
         const val units = "metric"

    }
}

