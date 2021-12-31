package com.kozak.myweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.kozak.myweather.db.DbManager
import com.kozak.myweather.db.WeatherDb
import com.kozak.myweather.internet.ApiBuilder
import com.kozak.myweather.internet.ApiCallBack
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val apiBuilder = ApiBuilder(this)
    val dbManager = DbManager(this)
    var city: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initiate the fused...
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()


        //Delete db on create app
        getApplicationContext().deleteDatabase(WeatherDb.DATABASE_NAME)

        //Spinner
        val locationName =
            arrayOf("London", "Paris", "Istanbul", "Tokyo", "Kyiv", "Current")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, locationName)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object:


            OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                apiBuilder.city = locationName[p2] //Sets the selected city to url
                city = locationName[p2] //Sets the selected city to db


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    //That will check the uses permission and receives data about the current location
    private fun fetchLocation(){
        val task: Task<Location> = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                return
        }
        //Receives our coordinates
        task.addOnSuccessListener {
            if (it != null){
                apiBuilder.lat = it.latitude
                apiBuilder.lon = it.longitude
            }
        }

    }



    //Insert data into the database and output to TextView
    fun  getWeather(){
        if (city != "Current") {
            //Handle retrofit callback
            apiBuilder.getCurrentDataCity(object : ApiCallBack {
                override fun onSuccess(t: Double) {
                    dbManager.insertToDb(city, t)
                    for (temp in dbManager.readDbDataTemperature()) {
                            tvText!!.text = "${temp}°"

                    }

                }

            })
        }else{
            //Handle retrofit callback
            apiBuilder.getCurrentDataLocation(object : ApiCallBack {
                override fun onSuccess(t: Double) {
                    dbManager.insertToDb(city, t)
                    for (temp in dbManager.readDbDataTemperature()) {
                            tvText!!.text = "${temp}°"

                    }

                }

            })

        }

    }


    //Weather call button
    fun onClickButton(view: View) {
        dbManager.openDb()
        getWeather()

    }

    override fun onDestroy() {
        dbManager.closeDb()
        super.onDestroy()
    }


}



