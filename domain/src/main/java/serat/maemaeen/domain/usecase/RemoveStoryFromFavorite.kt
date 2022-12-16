package serat.maemaeen.domain.usecase

import serat.maemaeen.domain.repository.StoryRepository

class RemoveStoryFromFavorite(private val storyRepository: StoryRepository) {
    suspend fun removeFromFav(storyId:Int) = storyRepository.removeStoryFromFavorite(storyId)
}