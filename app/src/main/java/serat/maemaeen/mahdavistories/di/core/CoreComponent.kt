package serat.maemaeen.mahdavistories.di.core

import dagger.Component
import serat.maemaeen.mahdavistories.di.feed.FeedModule
import serat.maemaeen.mahdavistories.di.feed.FeedSubComponent
import serat.maemaeen.mahdavistories.di.main.MainModule
import serat.maemaeen.mahdavistories.di.main.MainSubComponent
import serat.maemaeen.mahdavistories.di.core.modules.AppModule
import serat.maemaeen.mahdavistories.di.core.modules.DataBaseModule
import serat.maemaeen.mahdavistories.di.core.modules.DataModule
import serat.maemaeen.mahdavistories.di.core.modules.NetworkModule
import serat.maemaeen.mahdavistories.di.details.StoryDetailsModule
import serat.maemaeen.mahdavistories.di.details.StoryDetailsSubComponent
import serat.maemaeen.mahdavistories.di.favorites.FavoritesModule
import serat.maemaeen.mahdavistories.di.favorites.FavoritesSubComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class,DataBaseModule::class,DataModule::class])
interface CoreComponent {
    fun plus(mainModule: MainModule): MainSubComponent
    fun plus(feedModule: FeedModule): FeedSubComponent
    fun plus(storyDetailsModule: StoryDetailsModule): StoryDetailsSubComponent
    fun plus(favoritesModule: FavoritesModule): FavoritesSubComponent
}