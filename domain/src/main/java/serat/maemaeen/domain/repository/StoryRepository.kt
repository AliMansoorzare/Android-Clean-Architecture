package serat.maemaeen.domain.repository

import serat.maemaeen.domain.entiities.StoriesEntities
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.util.Result

interface StoryRepository {
   suspend fun getStories():Result<List<StoriesEntitiesItem>>
   suspend fun getStory(storyId: Int): Result<StoriesEntitiesItem>
   suspend fun getFavoriteStories(): Result<List<StoriesEntitiesItem>>
   suspend fun checkFavoriteStatus(storyId: Int):Result<Boolean>
   suspend fun addStoryToFavorite(storyId: Int)
   suspend fun removeStoryFromFavorite(storyId: Int)
}