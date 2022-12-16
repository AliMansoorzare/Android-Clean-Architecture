package serat.maemaeen.data.repository

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import serat.maemaeen.data.db.FavoriteStoryDao
import serat.maemaeen.data.entities.FavoriteStoriesDbData
import serat.maemaeen.data.exception.DataNotAvailableException
import serat.maemaeen.data.util.DiskExecutor
import serat.maemaeen.domain.util.Result

class FavoriteStoriesLocalDataSource(
    private val diskExecutor: DiskExecutor,
    private val favoriteStoryDao: FavoriteStoryDao
) : FavoriteStoriesDataSource.Local {
    override suspend fun getFavoriteStoryIds(): Result<List<FavoriteStoriesDbData>> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            val storyIds = favoriteStoryDao.getAll()
            return@withContext if (storyIds.isNotEmpty()) {
                Result.Success(storyIds)
            } else {
                Result.Error(DataNotAvailableException())
            }
        }

    override suspend fun addStoryToFavorite(storyId: Int) =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            favoriteStoryDao.add(FavoriteStoriesDbData(storyId))
        }

    override suspend fun removeStoryFromFavorite(storyId: Int) =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            favoriteStoryDao.remove(FavoriteStoriesDbData(storyId))
        }

    override suspend fun checkFavoriteStatus(storyId: Int): Result<Boolean> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            return@withContext Result.Success(favoriteStoryDao.get(storyId) != null)
        }
}