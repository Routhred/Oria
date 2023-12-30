package com.example.oria.viewModel.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.point.PointDetails
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * ViewModel for the new point view
 *
 * @property pointsRepository
 * @constructor
 *
 * @param savedStateHandle
 */
class PointEntryViewModel(
    savedStateHandle: SavedStateHandle, private val pointsRepository: PointRepository
) : ViewModel() {

    private val tripId: Int = checkNotNull(savedStateHandle["tripId"])
    var pointUiState by mutableStateOf(PointUiState())
        private set

    /**
     * Update current ui state to add new field
     *
     * @param pointDetails
     */
    fun updateUiState(pointDetails: PointDetails) {
        DEBUG(TagDebug.CREATE_POINT, "Update Ui State")
        pointUiState = PointUiState(
            pointDetails = pointDetails, isEntryValid = validateInput(pointDetails)
        )
    }

    /**
     * Save current Ui State item to the database
     *
     */
    suspend fun saveItem() {
        if (validateInput()) {
            DEBUG(TagDebug.CREATE_POINT, "Insertion in the database")
            updateUiState(
                pointUiState.pointDetails.copy(tripCode = tripId)
            )
            pointsRepository.insertPoint(pointUiState.pointDetails.toPoint())
        }else{
            ERROR(TagDebug.CREATE_POINT, "Current UI State Not Valid")
        }
    }

    /**
     * Valid the input
     *
     * @param uiState
     * @return if the current ui state is valid
     */
    private fun validateInput(uiState: PointDetails = pointUiState.pointDetails): Boolean {
        DEBUG(TagDebug.CREATE_POINT, "Validating Point ui state")
        return uiState.name.isNotBlank()/*return with(uiState){
            name.isNotBlank() &&
            location.isNotBlank() &&
            description.isNotBlank()
        }*/
    }

    /**
     * Create an image name with the timestamp
     *
     * @return the name of the image
     */
    fun getImageName(): String {
        DEBUG(TagDebug.CREATE_POINT, "Creation of the image name")
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        val date = LocalDateTime.now().format(formatter)
        return "ORIA_${pointUiState.pointDetails.name}_${tripId}_${date}"
    }

}

data class PointUiState(
    val pointDetails: PointDetails = PointDetails(), val isEntryValid: Boolean = false
)







