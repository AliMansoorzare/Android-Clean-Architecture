package serat.maemaeen.mahdavistories.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class ResourceProvider(context: Context) {
 private val appContext = context.applicationContext
    fun getDrawable(@DrawableRes resId:Int):Drawable?{
        return ContextCompat.getDrawable(appContext,resId)
    }
    fun getColor( int:Int): Int {
        return if (int == 1){
            1
        }else{
            2
        }
    }
}