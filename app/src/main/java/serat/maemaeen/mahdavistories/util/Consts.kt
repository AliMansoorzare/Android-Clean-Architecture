package serat.maemaeen.mahdavistories.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import serat.maemaeen.mahdavistories.R

//const val PATH_URL =  "https://maemaeen73.ir/Stories/"
fun Context.showToast(string: String){
    Toast.makeText(this,string, Toast.LENGTH_SHORT).show()
}

fun Activity.myWindow(){
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}
fun View.show() {
    visibility = View.VISIBLE
}


fun View.hide() {
    visibility = View.GONE
}

abstract class MyActivity : AppCompatActivity() {

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_alpha,R.anim.anim_alpha)


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_alpha,R.anim.anim_alpha)
    }
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.anim_alpha,R.anim.anim_alpha)

    }


}
