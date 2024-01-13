package com.example.oria.viewModel.database

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.data.storage.point.Point
import com.example.oria.backend.data.storage.point.PointDetails
import com.example.oria.backend.data.storage.trip.CurrentTrip
import com.example.oria.backend.data.storage.trip.TripDetails
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.backend.server.ImportTripResponse
import com.example.oria.backend.server.OriaClient
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import kotlinx.coroutines.launch

/**
 * View Model for the new trip view
 *
 * @property tripsRepository
 */
class ImportTripViewModel(
    private val tripsRepository: TripRepository,
    preferencesManager: PreferencesManager
) : ViewModel() {

    private lateinit var _navController: NavController
    private lateinit var _context: Context
    private val preferencesManager = preferencesManager
    var importTripState by mutableStateOf(ImportTripState())
        private set

    /**
     * Update current ui state with new field
     *
     * @param trip_id
     */
    fun updateUiState(trip_id: Int) {
        importTripState = ImportTripState(
            trip_id = trip_id
        )
    }

    fun importTrip(context: Context, navController: NavController){
        _navController = navController
        _context = context
        viewModelScope.launch{
            callImportTrip()
        }
    }

    suspend fun callImportTrip(){
        val response: ImportTripResponse =
            OriaClient.getInstance().importTrip(
                importTripState.trip_id,
                preferencesManager.getData(PreferencesKey.USERNAME,"")
            )
        val imported_trip: TripDetails = response.TRIP
        DEBUG(TagDebug.CREATE_TRIP, imported_trip.toString())
        tripsRepository.insertTrip(imported_trip.toTrip())
        CurrentTrip.getInstance().updateCurrentTripCode(imported_trip.id)
        _navController.navigate("main")
        importAllPoints(response.POINTS)
    }

    suspend fun importAllPoints(points: List<PointDetails>){
        for(point in points){

        }
    }

}

data class ImportTripState(
    val trip_id: Int = 0,
    val error_field: String = "",
    val tripDetails: TripDetails = TripDetails()
)