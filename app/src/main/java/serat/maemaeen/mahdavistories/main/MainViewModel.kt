package serat.maemaeen.mahdavistories.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.mahdavistories.base.BaseViewModel

class MainViewModel(dispatchers: DispatchersProvider) : BaseViewModel(dispatchers) {
    class Factory(private val dispatchers: DispatchersProvider) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(dispatchers) as T
        }

    }
}