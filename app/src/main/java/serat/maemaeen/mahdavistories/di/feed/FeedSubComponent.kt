package serat.maemaeen.mahdavistories.di.feed

import dagger.Subcomponent
import serat.maemaeen.mahdavistories.feed.FeedFragment

@Subcomponent(modules = [FeedModule::class])
interface FeedSubComponent {
fun inject(feedFragment: FeedFragment)
}