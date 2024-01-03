package com.example.parkinggent.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbParking)

    @Query("SELECT * from parking WHERE name = :name")
    fun getItem(name: String): Flow<DbParking>

    @Query("SELECT * from parking")
    fun getAllItems(): Flow<List<DbParking>>
}