package com.example.oria.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.oria.OriaApplication
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OriaViewModel(

): ViewModel() {
    companion object {

        const val TIMEOUT_MILLIS = 5_00L

        @Volatile
        private var instance: HttpClient? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: HttpClient().also { instance = it }
            }
    }



}

data class OriaUiState(
    val currentTripCode: Int = 0
)