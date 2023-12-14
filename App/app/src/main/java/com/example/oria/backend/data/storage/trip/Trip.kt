package com.example.oria.backend.data.storage.trip

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "location") val location: String = "",
    @ColumnInfo(name = "name")val name: String = "No trip Registered",
    @ColumnInfo(name = "date")val date: String = "",
    @ColumnInfo(name = "description")val description: String = "",
    val points: String = ""
)
