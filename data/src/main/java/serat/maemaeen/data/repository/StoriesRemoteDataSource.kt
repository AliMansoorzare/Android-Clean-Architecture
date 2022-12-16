package serat.maemaeen.data.repository

import kotlinx.coroutines.withContext
import serat.maemaeen.data.Api.StoryApi
import serat.maemaeen.data.mapper.StoryDataMapper
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.util.Result

class StoriesRemoteDataSource(
    private val dispatchersProvider: DispatchersProvider,
    private val storyApi: StoryApi
) : StoriesDataSource.Remote {
    override suspend fun getStories(): Result<List<StoriesEntitiesItem>> =
        withContext(dispatchersProvider.getIo()) {
            return@withContext try {
                val result = storyApi.getStories().await()
                Result.Success(result.stories.map { StoryDataMapper.toDomain(it) })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

}