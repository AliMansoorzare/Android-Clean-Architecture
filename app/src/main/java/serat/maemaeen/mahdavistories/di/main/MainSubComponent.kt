package serat.maemaeen.mahdavistories.di.main

import dagger.Subcomponent
import serat.maemaeen.mahdavistories.SplashActivity
import serat.maemaeen.mahdavistories.main.MainActivity

@Subcomponent(modules = [MainModule::class])
interface MainSubComponent {
    fun inject(mainActivity: MainActivity)
    fun splashInject(splashActivity: SplashActivity)
}