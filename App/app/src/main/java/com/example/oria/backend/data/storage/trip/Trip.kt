package com.example.oria.backend.data.storage.trip

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Data class for the trip
 *
 * @property id
 * @property location
 * @property name
 * @property date
 * @property description
 * @property points
 */
@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "location") val location: String = "",
    @ColumnInfo(name = "name") val name: String = "No trip Registered",
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    val points: String = ""
) {
    /**
     * Change a trip to TripDetails
     *
     * @return
     */
    fun toTripDetails(): TripDetails = TripDetails(
        id = id,
        name = name,
        location = location,
        date = date,
        description = description,
        points = points
    )
}

/**
 * Class for the tripDetails (easier to use than Trip)
 *
 * @property id
 * @property location
 * @property name
 * @property date
 * @property description
 * @property points
 */
@Serializable
data class TripDetails(
    val id: Int = 0,
    val location: String = "",
    val name: String = "",
    val date: String = "",
    val description: String = "",
    val points: String = ""
) {
    fun toTrip(): Trip = Trip(
        id = id,
        name = name,
        location = location,
        date = date,
        description = description,
        points = points
    )
}