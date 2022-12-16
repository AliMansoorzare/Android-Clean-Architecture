package serat.maemaeen.mahdavistories

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import serat.maemaeen.mahdavistories.base.BaseActivity
import serat.maemaeen.mahdavistories.base.BaseViewModel
import serat.maemaeen.mahdavistories.databinding.ActivitySplashBinding
import serat.maemaeen.mahdavistories.main.MainActivity
import serat.maemaeen.mahdavistories.main.MainViewModel
import serat.maemaeen.mahdavistories.util.myWindow
import soup.neumorphism.NeumorphImageView
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ViewBinding, MainViewModel>() {
    @Inject
    lateinit var factory: MainViewModel.Factory
    override fun inflateViewBinding(inflater: LayoutInflater): ViewBinding =
        ActivitySplashBinding.inflate(inflater)

    override fun createViewModel(): MainViewModel = ViewModelProvider(this, factory).get()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myWindow()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

    }
}