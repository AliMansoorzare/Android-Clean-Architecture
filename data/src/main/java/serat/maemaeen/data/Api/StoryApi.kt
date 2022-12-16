package serat.maemaeen.data.Api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import serat.maemaeen.data.entities.ModelDataStoryResponseItem

interface StoryApi {
        @GET("story.php")
        fun getStories(): Deferred<ModelDataStoryResponse>
}