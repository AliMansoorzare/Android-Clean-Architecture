package serat.maemaeen.mahdavistories.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.domain.usecase.GetStories
import serat.maemaeen.domain.util.onError
import serat.maemaeen.domain.util.onSuccess
import serat.maemaeen.mahdavistories.base.BaseViewModel
import serat.maemaeen.mahdavistories.entities.Story
import serat.maemaeen.mahdavistories.mapper.StoryEntityMapper
import serat.maemaeen.mahdavistories.util.GlideLoading
import serat.maemaeen.mahdavistories.util.SingleLiveEvent

class FeedViewModel internal constructor(
    private val getStories: GetStories,
    private val dispatchers: DispatchersProvider, val glideLoading: GlideLoading
) : BaseViewModel(dispatchers) {

    sealed class IdStory {
        data class StoryDetails(val storyId: Int,val storyMusicUrl:String ) : IdStory()
//        data class StoryMusUrl(val storyMusicUrl:String):IdStory()
    }



    sealed class UiState {
        data class FeedUiState(val stories: List<Story>) : UiState()
        data class Error(val message: String) : UiState()
        object Loading : UiState()
        object NotLoading : UiState()


    }

    private val idStory: MutableLiveData<IdStory> = MutableLiveData()
    private val musUrlStory: MutableLiveData<IdStory> = MutableLiveData()
    private val uiState: MutableLiveData<UiState> = MutableLiveData()
    fun onInitialState() = launchOnMainImmediate {
        loadStroies()
    }


    fun onStoryClicked(storyId: Int,musUrlStory:String) = launchOnMainImmediate {
        idStory.value = IdStory.StoryDetails(storyId,musUrlStory)
    }



    fun onStoryClickedState(): LiveData<IdStory> = idStory
    fun getUiState(): LiveData<UiState> = uiState
    suspend fun loadStroies() {
        uiState.value = UiState.Loading
        getStories.execute().onSuccess {
            uiState.value = UiState.NotLoading
            uiState.value = UiState.FeedUiState(it.map { storyEntity ->
                StoryEntityMapper.toStoryListItem(storyEntity)
            })
        }.onError {
            uiState.value = UiState.NotLoading
            uiState.value = UiState.Error(it.message.toString())
            Log.i("ve", "loadStroies: ${it.message.toString()}")
        }
    }


    class Factory(
        private val getStories: GetStories,
        private val dispatchers: DispatchersProvider, private val glideLoading: GlideLoading
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FeedViewModel(getStories, dispatchers, glideLoading) as T
        }
    }


}