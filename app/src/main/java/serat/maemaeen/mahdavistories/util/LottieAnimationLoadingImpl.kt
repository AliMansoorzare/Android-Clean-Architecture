package serat.maemaeen.mahdavistories.util

import com.airbnb.lottie.LottieAnimationView
import serat.maemaeen.mahdavistories.util.LottieAnimationLoading

class LottieAnimationLoadingImpl : LottieAnimationLoading {
    override fun load(imageView: LottieAnimationView, imageUrl: String) {
        imageView.setAnimationFromUrl(imageUrl)
    }
}