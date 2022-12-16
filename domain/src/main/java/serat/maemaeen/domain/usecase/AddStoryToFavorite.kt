package serat.maemaeen.domain.usecase

import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.util.Result

class AddStoryToFavorite(private val storyRepository: StoryRepository) {
    suspend fun addStoryToFavorite(storyId:Int) = storyRepository.addStoryToFavorite(storyId)
}