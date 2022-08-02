package com.example.weatherapp.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.constants.Constants.COLUMN_ADDRESS
import com.example.weatherapp.constants.Constants.COLUMN_LAT
import com.example.weatherapp.constants.Constants.COLUMN_LNG
import com.example.weatherapp.constants.Constants.TABLE_LOCATION

@Entity(tableName = TABLE_LOCATION)
data class Location(
    @PrimaryKey
    @ColumnInfo(name = "id") var id:Int,
    @ColumnInfo(name = COLUMN_LAT) var lat: Double?,
    @ColumnInfo(name = COLUMN_LNG) var lng: Double?,
    @ColumnInfo(name = COLUMN_ADDRESS) var address: String?
)