package serat.maemaeen.mahdavistories.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import serat.maemaeen.data.util.DispatchersProvider

abstract class BaseViewModel(dispatchers: DispatchersProvider) : ViewModel() {
    private val io = dispatchers.getIo()
    private val main = dispatchers.getMain()
    private val immediate = dispatchers.getMainImmediate()
    private fun CoroutineScope.launchOnMainImmediate(block: suspend CoroutineScope.() -> Unit): Job =
        launch(immediate, block = block)

    private fun CoroutineScope.launchOnMain(block: suspend CoroutineScope.() -> Unit): Job =
        launch(main, block = block)

    private fun CoroutineScope.launchOnIO(block: suspend CoroutineScope.() -> Unit): Job =
        launch(io, block = block)

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launchOnIO(block)

    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launchOnMain(block)

    protected fun launchOnMainImmediate(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launchOnMainImmediate(block)


}