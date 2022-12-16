package serat.maemaeen.mahdavistories.di.details

import dagger.Subcomponent
import serat.maemaeen.mahdavistories.storyDetails.StoryDetailsActivity

@Subcomponent(modules = [StoryDetailsModule::class])
interface StoryDetailsSubComponent {
    fun inject(storyDetailsActivity: StoryDetailsActivity)
}