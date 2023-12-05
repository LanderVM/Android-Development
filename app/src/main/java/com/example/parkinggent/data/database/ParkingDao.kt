package com.example.parkinggent.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.concurrent.Flow

@Dao
interface ParkingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbParking)

    @Update
    suspend fun update(item: dbParking)

    @Delete
    suspend fun delete(item: dbParking)

    @Query("SELECT * from parking WHERE name = :name")
    fun getItem(name: String): Flow<dbParking>

    @Query("SELECT * from parking ORDER BY name ASC")
    fun getAllItems(): Flow<List<dbParking>>
}