package com.example.oria.backend.data.storage.point

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    // Function to insert a new trip in the database
    suspend fun createPoint(point: Point)

    @Delete
    // Function to delete a trip
    suspend fun removePoint(point: Point)

    @Update
    suspend fun updatePoint(point: Point)

    @Query("SELECT * from points")
    fun getAllPoints(): Flow<List<Point>>

    @Query("SELECT * from points WHERE id = :id")
    fun getPoint(id: Int): Flow<Point?>

    @Query("SELECT * from points WHERE tripCode = :tripCode")
    fun getTripPoints(tripCode: Int): Flow<List<Point>>
}