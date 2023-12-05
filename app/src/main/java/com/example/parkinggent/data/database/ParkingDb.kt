package com.example.parkinggent.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [dbParking::class], version = 2, exportSchema = false)
abstract class TaskDb : RoomDatabase() {

    abstract fun parkingDao(): ParkingDao

    companion object {
        @Volatile
        private var Instance: TaskDb? = null

        fun getDatabase(context: Context): TaskDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskDb::class.java, "parking_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}