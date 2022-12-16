package serat.maemaeen.mahdavistories

import android.app.Application
import serat.maemaeen.mahdavistories.di.core.CoreComponent
import serat.maemaeen.mahdavistories.di.DaggerInjector
import serat.maemaeen.mahdavistories.di.core.DaggerCoreComponent
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
import serat.maemaeen.mahdavistories.favorite.FavoritesFragment
import serat.maemaeen.mahdavistories.feed.FeedFragment
import serat.maemaeen.mahdavistories.main.MainActivity
import serat.maemaeen.mahdavistories.storyDetails.StoryDetailsActivity

class App : Application(), DaggerInjector {
    private lateinit var coreComponent: CoreComponent
    override fun onCreate() {
        super.onCreate()



        coreComponent =
            DaggerCoreComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(PATH_URL))
                .dataBaseModule(DataBaseModule())
                .dataModule(DataModule())
                .build()
    }

    private fun createMainComponent(): MainSubComponent = coreComponent.plus(MainModule())
    private fun createFeedComponent():FeedSubComponent = coreComponent.plus(FeedModule())
    private fun createStoryDetailsComponent():StoryDetailsSubComponent = coreComponent.plus(StoryDetailsModule())
    private fun createFavoritesComponent(): FavoritesSubComponent = coreComponent.plus(
        FavoritesModule()
    )

    override fun <T> inject(view: T) {
        when (view) {
            is MainActivity -> createMainComponent().inject(view)
            is SplashActivity -> createMainComponent().splashInject(view)
            is FeedFragment -> createFeedComponent().inject(view)
            is StoryDetailsActivity -> createStoryDetailsComponent().inject(view)
            is FavoritesFragment -> createFavoritesComponent().inject(view)

        }
    }




}