package com.example.oria.backend.data.storage.trip

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database for the trips as abstract class
 *
 */
@Database(
    entities = [Trip::class],
    version = 1,
    exportSchema = false
)
abstract class TripDatabase : RoomDatabase() {
    abstract fun itemDao(): TripDao

    companion object {
        @Volatile
        private var Instance: TripDatabase? = null

        fun getDatabase(context: Context): TripDatabase {
            println("Get Database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TripDatabase::class.java,
                    "trips_database"
                )
                    .build()
                    .also {
                        Instance = it
                    }

            }
        }


    }
}