package com.example.oria.backend.data.storage.point

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Point::class],
    version = 1,
    exportSchema = false
)
abstract class PointDatabase: RoomDatabase() {
    abstract fun itemDao() : PointDao

    companion object {
        @Volatile
        private var Instance : PointDatabase? = null

        fun getDatabase(context: Context): PointDatabase {
            println("Get Database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PointDatabase::class.java, "points_database")
                    .build()
                    .also{
                        Instance = it
                    }

            }
        }


    }
}