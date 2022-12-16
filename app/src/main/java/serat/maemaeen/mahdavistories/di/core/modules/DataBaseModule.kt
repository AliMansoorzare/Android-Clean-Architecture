package serat.maemaeen.mahdavistories.di.core.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import serat.maemaeen.data.db.FavoriteStoryDao
import serat.maemaeen.data.db.FavoriteStoryDb
import serat.maemaeen.data.entities.StoryDbData
import serat.maemaeen.data.stories.StoriesDataBase
import serat.maemaeen.data.stories.StoryDao
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideStoryDataBase(context: Context): StoriesDataBase {
        return Room.databaseBuilder(context, StoriesDataBase::class.java, "story.db").build()
    }

    @Provides
    @Singleton
    fun provideFavoriteStoryDataBase(context: Context): FavoriteStoryDb {
        return Room.databaseBuilder(context, FavoriteStoryDb::class.java, "favorite_story.db")
            .build()
    }

    @Provides
    fun provideStoryDao(storiesDataBase: StoriesDataBase): StoryDao {
        return storiesDataBase.storyDao()
    }

    @Provides
    fun provideFavoriteMovieDao(favoriteStoryDb: FavoriteStoryDb): FavoriteStoryDao {
        return favoriteStoryDb.favoriteStoryDao()
    }
}