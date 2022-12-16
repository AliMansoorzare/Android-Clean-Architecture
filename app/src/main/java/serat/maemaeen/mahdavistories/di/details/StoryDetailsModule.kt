package serat.maemaeen.mahdavistories.di.details

import dagger.Module
import dagger.Provides
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.usecase.AddStoryToFavorite
import serat.maemaeen.domain.usecase.CheckFavoriteStatus
import serat.maemaeen.domain.usecase.GetStoryDetails
import serat.maemaeen.domain.usecase.RemoveStoryFromFavorite
import serat.maemaeen.mahdavistories.storyDetails.StoryDetailsViewModel
import serat.maemaeen.mahdavistories.util.ResourceProvider

@Module
class StoryDetailsModule {
    @Provides
    fun storyDetailsViewModelFactory(
        storyDetails: GetStoryDetails,
        checkFavoriteStatus: CheckFavoriteStatus,
        addStoryToFavorite: AddStoryToFavorite,
        removeStoryFromFavorite: RemoveStoryFromFavorite,
        resourceProvider: ResourceProvider,
        dispatchersProvider: DispatchersProvider
    ): StoryDetailsViewModel.Factory {
        return StoryDetailsViewModel.Factory(
            storyDetails = storyDetails,
            checkFavoriteStatus = checkFavoriteStatus,
            addStoryToFavorite = addStoryToFavorite,
            removeStoryFromFavorite = removeStoryFromFavorite,
            resourceProvider = resourceProvider,
            dispatchersProvider = dispatchersProvider
        )
    }
}