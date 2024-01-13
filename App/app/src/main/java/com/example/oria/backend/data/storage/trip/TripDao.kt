package com.example.oria.backend.data.storage.trip

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    // Function to insert a new trip in the database
    suspend fun createTrip(trip: Trip)

    @Delete
    // Function to delete a trip
    suspend fun removeTrip(trip: Trip)
    @Update
    suspend fun updateTrip(trip: Trip)

    @Query("SELECT * from trips")
    fun getAllTrips(): Flow<List<Trip>>
    @Query("SELECT * from trips WHERE id = :id")
    fun getTrip(id: Int): Flow<Trip?>

    @Query("SELECT * from trips WHERE id = (SELECT MAX(id) from trips)")
    fun getLastTrip(): Flow<Trip?>
}