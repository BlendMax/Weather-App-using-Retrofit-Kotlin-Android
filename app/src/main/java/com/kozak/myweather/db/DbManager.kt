package com.kozak.myweather.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager(context: Context) {
    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){ db = dbHelper.writableDatabase }

    fun insertToDb(title: String, content: Double){
        val values = ContentValues().apply {
            put(WeatherDb.COLUMN_NAME_TITLE, title)
            put(WeatherDb.COLUMN_NAME_TEMPERATURE, content)

        }
        db?.insert(WeatherDb.TABLE_NAME, null, values)
    }

    //Reading temperature from db
    fun readDbDataTemperature() : ArrayList<String> {
        val dataList = ArrayList<String>()
        val cursor = db?.query(WeatherDb.TABLE_NAME, null, null, null, null, null, null)

        with(cursor){
            while (this?.moveToNext()!!){
                val dataText =  cursor?.getString(cursor.getColumnIndexOrThrow(WeatherDb.COLUMN_NAME_TEMPERATURE))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList
    }

    //Reading city name from db
    fun readDbDataCity() : ArrayList<String> {
        val dataList = ArrayList<String>()
        val cursor = db?.query(WeatherDb.TABLE_NAME, null, null, null, null, null, null)

        with(cursor){
            while (this?.moveToNext()!!){
                val dataText =  cursor?.getString(cursor.getColumnIndexOrThrow(WeatherDb.COLUMN_NAME_TITLE))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList
    }

    fun closeDb(){
        dbHelper.close()
    }

}