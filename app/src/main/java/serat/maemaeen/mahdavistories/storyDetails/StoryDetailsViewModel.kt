package serat.maemaeen.mahdavistories.storyDetails

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.domain.usecase.AddStoryToFavorite
import serat.maemaeen.domain.usecase.CheckFavoriteStatus
import serat.maemaeen.domain.usecase.GetStoryDetails
import serat.maemaeen.domain.usecase.RemoveStoryFromFavorite
import serat.maemaeen.domain.util.Result
import serat.maemaeen.mahdavistories.R
import serat.maemaeen.domain.util.onSuccess
import serat.maemaeen.mahdavistories.base.BaseViewModel
import serat.maemaeen.mahdavistories.util.ResourceProvider
import serat.maemaeen.mahdavistories.util.SingleLiveEvent

class StoryDetailsViewModel internal constructor(
    private val storyId: Int,
    private val storyDetails: GetStoryDetails,
    private val checkFavoriteStatus: CheckFavoriteStatus,
    private val addStoryToFavorite: AddStoryToFavorite,
    private val removeStoryFromFavorite: RemoveStoryFromFavorite,
    private val resourceProvider: ResourceProvider, dispatchersProvider: DispatchersProvider
) : BaseViewModel(dispatchersProvider) {
    data class FavoriteState(val drawable: Drawable?, val int: Int)
    data class StoryDetailsUiState(
        val name: String,
        val imageUrl: String, val musicLink: String
    )


    private val storyDetailsUiState: MutableLiveData<StoryDetailsUiState> = MutableLiveData()
    private val favoriteState: MutableLiveData<FavoriteState> = MutableLiveData()


    fun onInitialState() = launchOnMainImmediate {
        load()
    }

    private suspend fun getStoryDetails(storyId: Int): Result<StoriesEntitiesItem> =
        storyDetails.getStory(storyId)

    private suspend fun checkFavStatus(storyId: Int): Result<Boolean> =
        checkFavoriteStatus.check(storyId)


    private suspend fun load() {
        getStoryDetails(storyId).onSuccess {
            storyDetailsUiState.value = StoryDetailsUiState(
                name = it.name,
                imageUrl = it.link_img,
                musicLink = it.link_music
            )
            checkFavStatus(storyId).onSuccess { isFavorite ->
                favoriteState.value =
                    FavoriteState(getFavoriteDrawable(isFavorite), getColor(isFavorite))
            }
        }
    }

    fun onFavoriteClicked() = launchOnMainImmediate {
        checkFavStatus(storyId).onSuccess {
            if (it) removeStoryFromFavorite.removeFromFav(storyId) else addStoryToFavorite.addStoryToFavorite(
                storyId
            )
            favoriteState.value = FavoriteState(getFavoriteDrawable(!it), getColor(!it))
        }
    }


    private fun getFavoriteDrawable(favorite: Boolean): Drawable? = if (favorite) {
        resourceProvider.getDrawable(R.drawable.ic_favorite_fill_white_48)
    } else {
        resourceProvider.getDrawable(R.drawable.ic_favorite_border_white_48)
    }

    private fun getColor(favorite: Boolean): Int = if (favorite) {
        resourceProvider.getColor(1)
    } else {
        resourceProvider.getColor(2)
    }

    fun getStoryDetailsUiStateLiveData(): LiveData<StoryDetailsUiState> = storyDetailsUiState
    fun getFavoriteStateLiveData(): LiveData<FavoriteState> = favoriteState

    class Factory(
        private val storyDetails: GetStoryDetails,
        private val checkFavoriteStatus: CheckFavoriteStatus,
        private val addStoryToFavorite: AddStoryToFavorite,
        private val removeStoryFromFavorite: RemoveStoryFromFavorite,
        private val resourceProvider: ResourceProvider,
        private val dispatchersProvider: DispatchersProvider
    ) : ViewModelProvider.Factory {

        var sId: Int = 0

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StoryDetailsViewModel(
                storyId = sId,
                storyDetails = storyDetails,
                checkFavoriteStatus = checkFavoriteStatus,
                addStoryToFavorite = addStoryToFavorite,
                removeStoryFromFavorite = removeStoryFromFavorite,
                resourceProvider = resourceProvider,
                dispatchersProvider = dispatchersProvider
            ) as T
        }
    }
}