package serat.maemaeen.data.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun getIo(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getMainImmediate(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}