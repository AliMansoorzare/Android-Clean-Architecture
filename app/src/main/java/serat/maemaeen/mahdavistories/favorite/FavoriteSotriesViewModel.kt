package serat.maemaeen.mahdavistories.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import serat.maemaeen.data.exception.DataNotAvailableException
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.usecase.GetFavoriteStories
import serat.maemaeen.domain.util.onError
import serat.maemaeen.domain.util.onSuccess
import serat.maemaeen.mahdavistories.base.BaseViewModel
import serat.maemaeen.mahdavistories.entities.Story
import serat.maemaeen.mahdavistories.mapper.StoryEntityMapper
import serat.maemaeen.mahdavistories.util.GlideLoading
import serat.maemaeen.mahdavistories.util.SingleLiveEvent

class FavoriteSotriesViewModel(
    private val getFavoriteStories: GetFavoriteStories,
    dispatchersProvider: DispatchersProvider,val glideLoading: GlideLoading
) : BaseViewModel(dispatchersProvider) {

    data class FavoriteUiState(
        val isLoading: Boolean = false,
        val noDataAvailable: Boolean = false,
        val stories: List<Story> = emptyList()
    )

    sealed class NavigationState {
        data class StoriesDetails(val storyId: Int) : NavigationState()
    }

    private val favoriteUiState: MutableLiveData<FavoriteUiState> = MutableLiveData()
    private val navigationState: SingleLiveEvent<NavigationState> = SingleLiveEvent()

    fun onResume() = launchOnMainImmediate {
        loadStories()
    }

    private suspend fun loadStories() {
        favoriteUiState.value = FavoriteUiState(isLoading = true)

        getFavoriteStories().onSuccess {
               showData(it)
            }.onError {
                when (it) {
                    is DataNotAvailableException -> showNoData()
                    else -> favoriteUiState.value = favoriteUiState.value?.copy(isLoading = false)
                }
            }
    }

    private fun showData(list: List<StoriesEntitiesItem>) {
        favoriteUiState.value = favoriteUiState.value?.copy(
            isLoading = false,
            noDataAvailable = false,
            stories = list.map { storyEntity -> StoryEntityMapper.toStoryListItem(storyEntity) }
        )
    }

    private fun showNoData() {
        favoriteUiState.value = favoriteUiState.value?.copy(
            isLoading = false,
            noDataAvailable = true,
            stories = emptyList()
        )
    }

    private suspend fun getFavoriteStories() = getFavoriteStories.getFavStories()

    fun onStoryClicked(movieId: Int) = launchOnMainImmediate {
        navigationState.value = NavigationState.StoriesDetails(movieId)
    }
    fun getFavoriteUiState(): LiveData<FavoriteUiState> = favoriteUiState
    fun getNavigateState(): LiveData<NavigationState> = navigationState

    class Factory(
        private val getFavoriteMovies: GetFavoriteStories,
        private val dispatchers: DispatchersProvider,val glideLoading: GlideLoading
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteSotriesViewModel(getFavoriteMovies, dispatchers,glideLoading) as T
        }
    }
}