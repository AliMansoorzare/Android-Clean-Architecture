package serat.maemaeen.mahdavistories.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import serat.maemaeen.mahdavistories.di.DaggerInjector

 abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    private val daggerInjector: DaggerInjector by lazy { application as DaggerInjector }
    protected val viewModel: VM by lazy { createViewModel() }
    protected val binding: VB by lazy { inflateViewBinding(layoutInflater) }
    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB
    protected abstract fun createViewModel(): VM

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         daggerInjector.inject(this)
         setContentView(binding.root)
     }


    protected fun <T> LiveData<T>.observer(observer: Observer<in T>) =
        observe(this@BaseActivity, observer)
}