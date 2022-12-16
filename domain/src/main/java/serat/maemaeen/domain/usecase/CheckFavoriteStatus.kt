package serat.maemaeen.domain.usecase

import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.util.Result

class CheckFavoriteStatus(private val storyRepository: StoryRepository) {
    suspend fun check(storyId:Int):Result<Boolean> = storyRepository.checkFavoriteStatus(storyId)
}