package serat.maemaeen.mahdavistories.di.favorites


import dagger.Module
import dagger.Provides
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.usecase.GetFavoriteStories
import serat.maemaeen.mahdavistories.favorite.FavoriteSotriesViewModel
import serat.maemaeen.mahdavistories.util.GlideLoading


@Module
class FavoritesModule {

    @Provides
    fun provideFavoritesViewModelFactory(
        dispatchersProvider: DispatchersProvider,
        getFavoriteStories: GetFavoriteStories, glideLoading: GlideLoading
    ): FavoriteSotriesViewModel.Factory {
        return FavoriteSotriesViewModel.Factory(getFavoriteStories, dispatchersProvider,glideLoading)
    }

}