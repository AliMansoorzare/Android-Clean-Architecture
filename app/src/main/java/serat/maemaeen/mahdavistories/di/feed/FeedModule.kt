package serat.maemaeen.mahdavistories.di.feed

import dagger.Module
import dagger.Provides
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.usecase.GetStories
import serat.maemaeen.mahdavistories.entities.Story
import serat.maemaeen.mahdavistories.feed.FeedViewModel
import serat.maemaeen.mahdavistories.util.GlideLoading
import serat.maemaeen.mahdavistories.util.NetCheck

@Module
class FeedModule {

    @Provides
    fun provideFeedViewModelFactory(
        getStories: GetStories,
        dispatchersProvider: DispatchersProvider,
        glideLoading: GlideLoading
    ): FeedViewModel.Factory =
        FeedViewModel.Factory(getStories, dispatchersProvider, glideLoading)
}