package serat.maemaeen.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DispatchersProviderImpl : DispatchersProvider{
    override fun getMainImmediate(): CoroutineDispatcher = Dispatchers.Main.immediate

    override fun getIo(): CoroutineDispatcher = Dispatchers.IO

    override fun getMain(): CoroutineDispatcher = Dispatchers.Main

    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default


}