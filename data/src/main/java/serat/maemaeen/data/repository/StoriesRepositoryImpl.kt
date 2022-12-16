package serat.maemaeen.data.repository

import serat.maemaeen.domain.entiities.StoriesEntities
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.util.Result
import serat.maemaeen.domain.util.getResult
import serat.maemaeen.domain.util.onSuccess

class StoriesRepositoryImpl(
    private val remote: StoriesDataSource.Remote,
    private val cache: StoriesDataSource.Cache,
    private val local: StoriesDataSource.Local,
    private val localFavorite: FavoriteStoriesDataSource.Local
) : StoryRepository {
    override suspend fun getStories(): Result<List<StoriesEntitiesItem>> = getStoriesFromCache()
    override suspend fun getStory(storyId: Int): Result<StoriesEntitiesItem> =
        getStoryFromCache(storyId)

    override suspend fun getFavoriteStories(): Result<List<StoriesEntitiesItem>> =
        getFavoriteStoriesFromLocal()

    override suspend fun checkFavoriteStatus(storyId: Int): Result<Boolean> =localFavorite.checkFavoriteStatus(storyId)

    override suspend fun addStoryToFavorite(storyId: Int) = localFavorite.addStoryToFavorite(storyId)

    override suspend fun removeStoryFromFavorite(storyId: Int) = localFavorite.removeStoryFromFavorite(storyId)

    private suspend fun getStoryFromCache(storyId: Int): Result<StoriesEntitiesItem> =
        cache.getStory(storyId).getResult({
            it
        }, {
            getStoryFromLocal(storyId)
        })

    private suspend fun getStoryFromLocal(storyId: Int): Result<StoriesEntitiesItem> =
        local.getStory(storyId)

    private suspend fun getStoriesFromCache(): Result<List<StoriesEntitiesItem>> =
        cache.getStories().getResult({
            it
        }, {
            getStoriesFromLocal()
        })

    private suspend fun getStoriesFromLocal(): Result<List<StoriesEntitiesItem>> =
        local.getStories().getResult({
            refreshCache(it.data)
            it
        }, {
            getStoriesFromRemote()
        }
        )

    private suspend fun getStoriesFromRemote(): Result<List<StoriesEntitiesItem>> =
        remote.getStories().onSuccess {
            saveStories(it)
            refreshCache(it)
        }

    private suspend fun saveStories(storiesEntitiesItem: List<StoriesEntitiesItem>) {
        local.saveStories(storiesEntitiesItem)
    }

    private suspend fun refreshCache(storiesEntitiesItem: List<StoriesEntitiesItem>) {
        cache.saveStories(storiesEntitiesItem)
    }

    private suspend fun getFavoriteStoriesFromLocal(): Result<List<StoriesEntitiesItem>> {
        return localFavorite.getFavoriteStoryIds().getResult({
            local.getFavoriteStories(it.data.map { it.storyId })
        }, {
            Result.Error(it.error)
        })
    }
}