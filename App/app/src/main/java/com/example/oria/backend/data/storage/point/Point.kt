package com.example.oria.backend.data.storage.point

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.oria.viewModel.database.PointUiState
import kotlinx.serialization.Serializable


@Entity(tableName = "points")
data class Point(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "location") val location: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "tripCode") val tripCode: Int = 0,
) {
    fun toPointUiState(isEntryValid: Boolean = false): PointUiState = PointUiState(
        pointDetails = this.toPointDetails(), isEntryValid = isEntryValid
    )

    fun toPointDetails(): PointDetails = PointDetails(
        id = id,
        name = name,
        location = location,
        description = description,
        tripCode = tripCode,
        image = image
    )
}

@Serializable
data class PointDetails(
    val id: Int = 0,
    val location: String = "",
    val name: String = "",
    val description: String = "",
    val tripCode: Int = 0,
    val image: String = ""
) {
    fun toPoint(): Point = Point(
        id = id,
        name = name,
        location = location,
        description = description,
        tripCode = tripCode,
        image = image
    )

}