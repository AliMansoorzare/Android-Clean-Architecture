package serat.maemaeen.domain.usecase

import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.util.Result

class GetStoryDetails(private val storyRepository: StoryRepository) {
    suspend fun getStory(storyId:Int):Result<StoriesEntitiesItem> = storyRepository.getStory(storyId)
}