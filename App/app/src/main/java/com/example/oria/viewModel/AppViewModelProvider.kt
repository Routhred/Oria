package com.example.oria.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.oria.OriaApplication
import com.example.oria.viewModel.database.PointEntryViewModel
import com.example.oria.viewModel.database.TripEntryViewModel
import com.example.oria.viewModel.trip.GalleryViewModel
import com.example.oria.viewModel.trip.TripViewModel


object AppViewModelProvider {
    val TripFactory = viewModelFactory {
        initializer {
            TripEntryViewModel(
                oriaApplication().container.tripsRepository
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
    }
    val PointFactory = viewModelFactory {
        initializer {
            PointEntryViewModel(
                this.createSavedStateHandle(),
                oriaApplication().container.pointsRepository
            )
        }
    }

}
fun CreationExtras.oriaApplication(): OriaApplication =
    (this[APPLICATION_KEY] as OriaApplication)
