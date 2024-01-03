package com.example.parkinggent.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

/**
 * Database class with a singleton Instance object.
 */
// 1. Type Converters
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromLocationAndDimension(value: LocationAndDimension): String {
        val type = object : TypeToken<LocationAndDimension>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLocationAndDimension(value: String): LocationAndDimension {
        val type = object : TypeToken<LocationAndDimension>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLocation(value: Location): String {
        val type = object : TypeToken<Location>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLocation(value: String): Location {
        val type = object : TypeToken<Location>() {}.type
        return gson.fromJson(value, type)
    }
}

// 2. Updated Room Database with Type Converters
@Database(entities = [DbParking::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ParkingDb : RoomDatabase() {
    abstract fun parkingDao(): ParkingDao

    companion object {
        @Volatile
        private var Instance: ParkingDb? = null

        fun getDatabase(context: Context): ParkingDb {
            return Instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDb::class.java,
                    "parking_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Instance = db
                db
            }
        }
    }
}