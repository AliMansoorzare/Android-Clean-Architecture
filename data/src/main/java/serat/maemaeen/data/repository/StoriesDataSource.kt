package serat.maemaeen.data.repository
import serat.maemaeen.domain.util.Result
import serat.maemaeen.domain.entiities.StoriesEntities
import serat.maemaeen.domain.entiities.StoriesEntitiesItem

interface StoriesDataSource {
    interface Remote {
        suspend fun getStories(): Result<List<StoriesEntitiesItem>>
    }

    interface Local : Remote {
        suspend fun getStory(storyId: Int): Result<StoriesEntitiesItem>
        suspend fun saveStories(storiesEntitiesItem: List<StoriesEntitiesItem>)
        suspend fun getFavoriteStories(storyIds: List<Int>): Result<List<StoriesEntitiesItem>>
    }

    interface Cache : Local
}