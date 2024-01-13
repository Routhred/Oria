package com.example.oria.viewModel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.oria.OriaApplication
import com.example.oria.viewModel.auth.LoginViewModel
import com.example.oria.viewModel.auth.RegisterViewModel
import com.example.oria.viewModel.database.ImportTripViewModel
import com.example.oria.viewModel.database.PointEntryViewModel
import com.example.oria.viewModel.database.TripEntryViewModel
import com.example.oria.viewModel.global.HomeViewModel
import com.example.oria.viewModel.trip.GalleryViewModel
import com.example.oria.viewModel.trip.PointViewModel
import com.example.oria.viewModel.trip.TripViewModel

/**
 * Provide ViewModel for all view in the projet
 */
object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            TripEntryViewModel(
                oriaApplication().container.tripsRepository,
                oriaApplication().container.preferencesManager
            )
        }
        initializer {
            ImportTripViewModel(
                oriaApplication().container.tripsRepository,
                oriaApplication().container.preferencesManager
            )
        }
        initializer {
            GalleryViewModel(oriaApplication().container.tripsRepository)
        }
        initializer {
            TripViewModel(
                this.createSavedStateHandle(),
                oriaApplication().container.tripsRepository,
                oriaApplication().container.pointsRepository
            )
        }
        initializer {
            HomeViewModel(
                oriaApplication().container.tripsRepository
            )
        }
        initializer {
            LoginViewModel(
                oriaApplication().container.preferencesManager
            )
        }
        initializer {
            RegisterViewModel(
                oriaApplication().container.preferencesManager
            )
        }
        initializer {
            PointEntryViewModel(
                this.createSavedStateHandle(),
                oriaApplication().container.pointsRepository,
                oriaApplication().container.preferencesManager
            )
        }
        initializer {
            PointViewModel(
                this.createSavedStateHandle(),
                oriaApplication().container.pointsRepository
            )
        }
    }

}
fun CreationExtras.oriaApplication(): OriaApplication =
    (this[APPLICATION_KEY] as OriaApplication)
