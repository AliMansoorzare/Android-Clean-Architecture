package serat.maemaeen.mahdavistories.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import serat.maemaeen.mahdavistories.PlayService
import serat.maemaeen.mahdavistories.base.BaseActivity
import serat.maemaeen.mahdavistories.databinding.ActivityMainBinding
import serat.maemaeen.mahdavistories.util.myWindow
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var factory: MainViewModel.Factory
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(inflater)
//private val navController by lazy { binding.fragmentContainerView?.getFragment<NavHostFragment>()?.navController }
    override fun createViewModel(): MainViewModel = ViewModelProvider(this, factory).get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myWindow()
//        if (savedInstanceState == null) {
//            binding.menu?.setItemSelected(R.id.FeedFragment, true)
//        }

        startPlayService()
    }

//    private fun setUpViews() {
//        setupNavigationView()
//    }

//    private fun setupNavigationView() = with(binding.navigationView) {
//        if (this is NavigationBarView) navController?.let { setupWithNavController(it) }
//    }

//    private fun setUpNavigationView() =
//        binding.menu?.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
//            var fragment: Fragment? = null
//            override fun onItemSelected(id: Int) {
//                when (id) {
//                    R.id.FeedFragment -> fragment = FeedFragment()
//                    R.id.loginFragment -> fragment = LoginFragment()
//                    R.id.roomDataFragment -> fragment = FavoritesFragment()
//                }
//                if (fragment != null) {
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.replace(R.id.fragmentContainerView, fragment!!)
//                    transaction.commit()
//                }
//            }
//
//        })

    private fun startPlayService() {
        val intent = Intent(this, PlayService::class.java)
        startService(intent)
    }
}