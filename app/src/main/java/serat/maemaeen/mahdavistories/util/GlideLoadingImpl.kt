package serat.maemaeen.mahdavistories.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import serat.maemaeen.mahdavistories.R

class GlideLoadingImpl: GlideLoading {
    override fun load(imageView: ImageView,img: ImageView, uri: String) {
        Glide.with(imageView).load(uri).placeholder(R.drawable.main).error(R.drawable.main).into(img)
    }


}