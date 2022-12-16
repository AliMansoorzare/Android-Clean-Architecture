package serat.maemaeen.data.repository

import serat.maemaeen.data.entities.FavoriteStoriesDbData
import serat.maemaeen.domain.util.Result
interface FavoriteStoriesDataSource {
    interface Local {
        suspend fun getFavoriteStoryIds(): Result<List<FavoriteStoriesDbData>>
        suspend fun addStoryToFavorite(storyId: Int)
        suspend fun removeStoryFromFavorite(storyId: Int)
        suspend fun checkFavoriteStatus(storyId: Int): Result<Boolean>
    }
}