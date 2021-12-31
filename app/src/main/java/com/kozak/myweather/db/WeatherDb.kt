package com.kozak.myweather.db

import android.provider.BaseColumns

object WeatherDb {
    const val TABLE_NAME = "current_weather"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_TEMPERATURE = "content"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "CurrentWeatherDB.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT," +
            "$COLUMN_NAME_TEMPERATURE REAL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}