package serat.maemaeen.data.repository

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import serat.maemaeen.data.exception.DataNotAvailableException
import serat.maemaeen.data.mapper.StoryDataMapper
import serat.maemaeen.data.stories.StoryDao
import serat.maemaeen.data.util.DiskExecutor
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.util.Result

class StoriesLocalDataSource(
    private val diskExecutor: DiskExecutor,
    private val storyDao: StoryDao
) : StoriesDataSource.Local {
    override suspend fun getStory(storyId: Int): Result<StoriesEntitiesItem> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            return@withContext storyDao.getStory(storyId)?.let {
                Result.Success(StoryDataMapper.toDomain(it))
            } ?: Result.Error(DataNotAvailableException())
        }

    override suspend fun saveStories(storiesEntitiesItem: List<StoriesEntitiesItem>) =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            storyDao.saveStories(storiesEntitiesItem.map { StoryDataMapper.toDbData(it) })
        }

    override suspend fun getFavoriteStories(storyIds: List<Int>): Result<List<StoriesEntitiesItem>> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            return@withContext Result.Success(
                storyDao.getFavoriteStories(storyIds).map { StoryDataMapper.toDomain(it) })
        }

    override suspend fun getStories(): Result<List<StoriesEntitiesItem>> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            val stories = storyDao.getStories()
            return@withContext if (stories.isNotEmpty()) {
                Result.Success(
                    stories.map { StoryDataMapper.toDomain(it) })
            } else {
                Result.Error(DataNotAvailableException())
            }
        }
}