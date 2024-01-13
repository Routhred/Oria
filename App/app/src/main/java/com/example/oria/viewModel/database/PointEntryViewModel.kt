package com.example.oria.viewModel.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.data.storage.point.PointDetails
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.server.CreatePointResponse
import com.example.oria.backend.server.OriaClient
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.ERROR_SERVER
import com.example.oria.ui.theme.EXIT_ERROR
import com.example.oria.ui.theme.NO_ERROR
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
    savedStateHandle: SavedStateHandle,
    private val pointsRepository: PointRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    // TODO Create points on server
    // TODO Import points from server
    // TODO DElete points on server
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
    suspend fun saveItem(): Int{

        val response: CreatePointResponse =
            OriaClient.getInstance().createPoint(
                pointUiState.pointDetails,
                preferencesManager.getData(PreferencesKey.USERNAME, ""),
                tripId
            )

        val error_code = response.ERROR_CODE

        // TODO Handle error
        if (error_code != NO_ERROR) {
            when (error_code) {
                ERROR_SERVER -> {
                    pointUiState = pointUiState.copy(error_field = "Server error")
                }

                else -> {
                    pointUiState = pointUiState.copy(error_field = "Bad request")
                }
            }
            ERROR(TagDebug.CREATE_TRIP, pointUiState.error_field)
            return EXIT_ERROR
        }

        updateUiState(pointUiState.pointDetails.copy(
            id=response.POINT_ID,
            tripCode = tripId
        ))


        if (validateInput()) {
            DEBUG(TagDebug.CREATE_POINT, "Insertion in the database : ${
                pointUiState.pointDetails
            }"
            )
            pointsRepository.insertPoint(pointUiState.pointDetails.toPoint())
        }else{
            ERROR(TagDebug.CREATE_POINT, "Current UI State Not Valid")
            return EXIT_ERROR
        }
        return response.POINT_ID
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
    val pointDetails: PointDetails = PointDetails(),
    val isEntryValid: Boolean = false,
    val error_field: String = ""
)







