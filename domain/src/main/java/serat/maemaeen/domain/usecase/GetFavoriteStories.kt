package serat.maemaeen.domain.usecase

import androidx.lifecycle.LiveData
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.util.Result

class GetFavoriteStories(private val storyRepository: StoryRepository) {
    suspend fun getFavStories():Result<List<StoriesEntitiesItem>> = storyRepository.getFavoriteStories()
}