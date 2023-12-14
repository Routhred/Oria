package com.example.oria.backend.data.storage.point

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class Point(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val location: String,
    val name: String,
    val image: String,
    val description: String,
    val tripCode: Int,
)
