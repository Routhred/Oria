package com.example.oria.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.oria.OriaApplication
import com.example.oria.viewModel.database.PointEntryViewModel
import com.example.oria.viewModel.database.TripEntryViewModel
import com.example.oria.viewModel.trip.GalleryViewModel


object AppViewModelProvider {
    val TripFactory = viewModelFactory {
        initializer {
            TripEntryViewModel(
                oriaApplication().container.tripsRepository,
                oriaApplication().storeData
            )
        }
        initializer {
            GalleryViewModel(oriaApplication().container.tripsRepository)
        }
        initializer {
            HomeViewModel(
                oriaApplication().container.tripsRepository,
                oriaApplication().storeData
            )
        }
    }
    val PointFactory = viewModelFactory {
        initializer {
            PointEntryViewModel(
                oriaApplication().container.pointsRepository
            )
        }
    }

}
fun CreationExtras.oriaApplication(): OriaApplication =
    (this[APPLICATION_KEY] as OriaApplication)
