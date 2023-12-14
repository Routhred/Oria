package com.example.oria.viewModel.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.point.Point
import com.example.oria.backend.data.storage.point.PointRepository

class PointEntryViewModel (private val pointsRepository: PointRepository): ViewModel(){

    var pointUiState by mutableStateOf(PointUiState())
        private set

    fun updateUiState(pointDetails: PointDetails){
        pointUiState = PointUiState(
            pointDetails = pointDetails,
            isEntryValid = validateInput(pointDetails)
        )
    }

    suspend fun saveItem(){
        if(validateInput()){
            pointsRepository.insertPoint(pointUiState.pointDetails.toPoint())
        }
    }

    private fun validateInput(uiState: PointDetails = pointUiState.pointDetails): Boolean{
        return with(uiState){
            name.isNotBlank() &&
            location.isNotBlank() &&
            description.isNotBlank()
        }
    }

}

data class PointUiState(
    val pointDetails: PointDetails = PointDetails(),
    val isEntryValid: Boolean = false
)

data class PointDetails(
    val id: Int = 0,
    val location: String = "",
    val name: String = "",
    val description: String = "",
    val tripCode: Int = 0,
    val image: String = ""
)

fun PointDetails.toPoint(): Point = Point(
    id = id,
    name = name,
    location = location,
    description = description,
    tripCode = tripCode,
    image = image
)

fun Point.toPointUiState(isEntryValid: Boolean = false): PointUiState = PointUiState(
    pointDetails = this.toPointDetails(),
    isEntryValid = isEntryValid
)

fun Point.toPointDetails(): PointDetails = PointDetails(
    id = id,
    name = name,
    location = location,
    description = description,
    tripCode = tripCode,
    image = image
)