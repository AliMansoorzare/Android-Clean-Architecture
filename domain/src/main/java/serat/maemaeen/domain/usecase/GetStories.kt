package serat.maemaeen.domain.usecase

import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.util.Result

open class GetStories(private val storyRepository: StoryRepository) {
    suspend fun execute(): Result<List<StoriesEntitiesItem>> = storyRepository.getStories()
}