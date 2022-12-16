package serat.maemaeen.mahdavistories.di

interface DaggerInjector {
    val PATH_URL: String
        get() = "https://maemaeen73.ir/Stories/"

    fun <T>inject(view:T)
}