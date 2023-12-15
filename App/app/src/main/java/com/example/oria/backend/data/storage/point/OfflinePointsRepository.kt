package com.example.oria.backend.data.storage.point

import kotlinx.coroutines.flow.Flow

class OfflinePointsRepository(private val pointDao: PointDao) : PointRepository {
    override fun getAllPointsStream(): Flow<List<Point>> = pointDao.getAllPoints()

    override fun getPointStream(id: Int): Flow<Point?> = pointDao.getPoint(id)
    override fun getTripPoints(tripCode: Int): Flow<List<Point>> = pointDao.getTripPoints(tripCode)

    override suspend fun insertPoint(item: Point) = pointDao.createPoint(item)

    override suspend fun deletePoint(item: Point) = pointDao.removePoint(item)

    override suspend fun updatePoint(item: Point) = pointDao.updatePoint(item)
}