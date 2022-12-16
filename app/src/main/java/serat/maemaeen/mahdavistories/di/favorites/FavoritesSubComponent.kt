package serat.maemaeen.mahdavistories.di.favorites

import dagger.Subcomponent
import serat.maemaeen.mahdavistories.di.favorites.FavoritesModule
import serat.maemaeen.mahdavistories.favorite.FavoritesFragment


@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesSubComponent {
    fun inject(favoritesFragment: FavoritesFragment)
}