package com.example.oria.backend.data.storage.point

import kotlinx.coroutines.flow.Flow

interface PointRepository {
    fun getAllPointsStream(): Flow<List<Point>>
    fun getPointStream(id: Int): Flow<Point?>

    fun getTripPoints(tripCode: Int): Flow<List<Point>>
    suspend fun insertPoint(item: Point)
    suspend fun deletePoint(item: Point)
    suspend fun updatePoint(item: Point)
    suspend fun nukeTable()
}