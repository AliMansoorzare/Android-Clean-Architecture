package serat.maemaeen.data.repository

import android.provider.ContactsContract
import android.util.SparseArray
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import serat.maemaeen.data.exception.DataNotAvailableException
import serat.maemaeen.data.util.DiskExecutor
import serat.maemaeen.domain.entiities.StoriesEntities
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.util.Result

class StoriesCatchDataSource(private val diskExecutor: DiskExecutor) : StoriesDataSource.Cache {
    private val inMemoryCatch = SparseArray<StoriesEntitiesItem>()
    override suspend fun getStory(storyId: Int): Result<StoriesEntitiesItem> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            val story = inMemoryCatch.get(storyId)
            return@withContext if (story != null) {
                Result.Success(story)
            } else {
                Result.Error(DataNotAvailableException())
            }
        }

    override suspend fun saveStories(storiesEntitiesItem: List<StoriesEntitiesItem>) =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            inMemoryCatch.clear()
            for (story in storiesEntitiesItem) inMemoryCatch.put(story.id, story)
        }

    override suspend fun getFavoriteStories(storyIds: List<Int>): Result<List<StoriesEntitiesItem>> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            return@withContext if (inMemoryCatch.size() > 0) {
                val stories = arrayListOf<StoriesEntitiesItem>()
                storyIds.forEach { id -> stories.add(inMemoryCatch.get(id)) }
                Result.Success(stories)
            } else {
                Result.Error(DataNotAvailableException())
            }
        }

    override suspend fun getStories(): Result<List<StoriesEntitiesItem>> =
        withContext(diskExecutor.asCoroutineDispatcher()) {
            return@withContext if (inMemoryCatch.size() > 0) {
                val stories = arrayListOf<StoriesEntitiesItem>()
                for (i in 0 until inMemoryCatch.size()) {
                    val key = inMemoryCatch.keyAt(i)
                    stories.add(inMemoryCatch[key])
                }
                Result.Success(stories)
            } else {
                Result.Error(DataNotAvailableException())
            }

        }
}