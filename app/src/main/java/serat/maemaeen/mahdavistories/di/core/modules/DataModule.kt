package serat.maemaeen.mahdavistories.di.core.modules

import dagger.Module
import dagger.Provides
import retrofit2.http.POST
import serat.maemaeen.data.Api.StoryApi
import serat.maemaeen.data.db.FavoriteStoryDao
import serat.maemaeen.data.repository.*
import serat.maemaeen.data.stories.StoryDao
import serat.maemaeen.data.util.DiskExecutor
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.repository.StoryRepository
import serat.maemaeen.domain.usecase.*
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideStoryRepository(
        storyRemote: StoriesDataSource.Remote,
        storyLocal: StoriesDataSource.Local,
        storyCache: StoriesDataSource.Cache,
        storyFavorite: FavoriteStoriesDataSource.Local
    ): StoryRepository {
        return StoriesRepositoryImpl(storyRemote, storyCache, storyLocal, storyFavorite)
    }

    @Provides
    @Singleton
    fun provideStoryLocalDataSource(
        storyDao: StoryDao,
        diskExecutor: DiskExecutor
    ): StoriesDataSource.Local {
        return StoriesLocalDataSource(diskExecutor, storyDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteStoryLocalDataSource(
        diskExecutor: DiskExecutor,
        favoriteStoryDao: FavoriteStoryDao
    ): FavoriteStoriesDataSource.Local {
        return FavoriteStoriesLocalDataSource(diskExecutor, favoriteStoryDao)
    }

    @Provides
    @Singleton
    fun provideStoryCachDataSource(diskExecutor: DiskExecutor): StoriesDataSource.Cache {
        return StoriesCatchDataSource(diskExecutor)
    }

    @Provides
    @Singleton
    fun provideStoryRemoteDataSource(
        dispatchersProvider: DispatchersProvider, storyApi: StoryApi
    ): StoriesDataSource.Remote = StoriesRemoteDataSource(dispatchersProvider, storyApi)

    @Provides
    fun provideGetStoryUseCase(storyRepository: StoryRepository): GetStories {
        return GetStories(storyRepository)
    }

    @Provides
    fun provideGetStoryDetailsUseCase(storyRepository: StoryRepository): GetStoryDetails {
        return GetStoryDetails(storyRepository)
    }

    @Provides
    fun provideGetFavoriteStoriesUseCase(storyRepository: StoryRepository): GetFavoriteStories {
        return GetFavoriteStories(storyRepository)
    }

    @Provides
    fun provideCheckFavoriteStatusUseCase(storyRepository: StoryRepository): CheckFavoriteStatus {
        return CheckFavoriteStatus(storyRepository)
    }

    @Provides
    fun provideAddStoryToFavoriteUseCase(storyRepository: StoryRepository): AddStoryToFavorite {
        return AddStoryToFavorite(storyRepository)
    }

    @Provides
    fun provideRemoveStoryFromFavoriteUseCase(storyRepository: StoryRepository): RemoveStoryFromFavorite {
        return RemoveStoryFromFavorite(storyRepository)
    }
}