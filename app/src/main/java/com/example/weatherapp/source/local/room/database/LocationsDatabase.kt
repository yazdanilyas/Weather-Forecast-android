package com.example.weatherapp.source.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.customData.models.room.Locations
import com.example.weatherapp.source.local.room.dao.LocationDao
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Locations::class], version = 6, exportSchema = false)
abstract class LocationsDatabase  : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        private var INSTANCE: LocationsDatabase? = null

        @JvmStatic
        fun getDatabaseInstance(@ApplicationContext context: Context): LocationsDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    LocationsDatabase::class.java,
                    "allLocations"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as LocationsDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}