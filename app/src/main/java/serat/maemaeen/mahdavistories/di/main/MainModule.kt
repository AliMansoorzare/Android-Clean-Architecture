package serat.maemaeen.mahdavistories.di.main

import dagger.Module
import dagger.Provides
import serat.maemaeen.data.util.DispatchersProvider
import serat.maemaeen.mahdavistories.main.MainViewModel

@Module
class MainModule {

    @Provides
    fun provideMainViewModelFactory(dispatchersProvider: DispatchersProvider): MainViewModel.Factory =
        MainViewModel.Factory(dispatchersProvider)
}