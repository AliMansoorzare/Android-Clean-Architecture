package serat.maemaeen.mahdavistories.feed

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.serat.maemaeen73.util.CusDiaOperExitSoft_b
import serat.maemaeen.mahdavistories.PlayService
import serat.maemaeen.mahdavistories.adapter.StoryAdapter
import serat.maemaeen.mahdavistories.base.BaseFragment
import serat.maemaeen.mahdavistories.databinding.FragmentFeedBinding
import serat.maemaeen.mahdavistories.storyDetails.StoryDetailsActivity
import serat.maemaeen.mahdavistories.util.CusDia
import javax.inject.Inject


class FeedFragment : BaseFragment<FragmentFeedBinding, FeedViewModel>() {


    @Inject
    lateinit var factory: FeedViewModel.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alpha()
        navigation()
    }

    private fun navigation() {
        binding.apply {
            close.setOnClickListener {
                exit()
            }
            chat.setOnClickListener(toChatFragment)
            fav.setOnClickListener(toFavoriteFragment)
        }
    }

    private fun exit() {
        val cusDiaOperExitSoft_b: CusDia = CusDiaOperExitSoft_b(
            this.context as Activity, " خروج از برنامه",
            "داری میری؟",
            "آره",
            "نه"
        )
        cusDiaOperExitSoft_b.DoIt()
    }

    private val toChatFragment: (View) -> Unit =
        {
            findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToLoginFragment())
        }
    private val toFavoriteFragment: (View) -> Unit =
        { findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToFavoritesFragment()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onInitialState()
        setUpViews()
        observerViewModel()
    }

    private val storyAdapter by lazy {
        StoryAdapter(
            viewModel.glideLoading,
            viewModel::onStoryClicked

        )
    }


    override fun createViewModel(): FeedViewModel = ViewModelProvider(this, factory).get()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFeedBinding =
        FragmentFeedBinding.inflate(inflater)

    private fun setUpViews() {
        setUpRecycler()
    }

    private fun setUpRecycler() {
        binding.apply {
            FeedFragmentRecycler.layoutManager = GridLayoutManager(context, 2)

        }

    }


    private fun observerViewModel() = with(viewModel) {


        onStoryClickedState().observe {
            when (it) {
                is FeedViewModel.IdStory.StoryDetails -> showOrNavigateToStoryDetails(
                    it.storyId,
                    it.storyMusicUrl
                )

            }


        }


        getUiState().observe {
            when (it) {
                is FeedViewModel.UiState.Loading -> binding.progressBar.visibility =
                    View.VISIBLE
                is FeedViewModel.UiState.NotLoading -> binding.progressBar.visibility =
                    View.GONE
                is FeedViewModel.UiState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is FeedViewModel.UiState.FeedUiState -> {
                    with(binding) {
                        FeedFragmentRecycler.visibility = View.VISIBLE
                        storyAdapter.setData(it.stories)
                        FeedFragmentRecycler.adapter = storyAdapter
                        firsTime()
                    }


                }

            }

        }

    }

    private fun firsTime() {
        val settings = activity?.getSharedPreferences("MY_PREFERENCESS", 0)
        val ret = settings?.getBoolean("activity.firstVisitt", true)
        if (ret == true) {
            val editor: SharedPreferences.Editor = settings.edit()
            editor.putBoolean("activity.firstVisitt", false)
            editor.apply()
            feedScreen()
        } else {
            // TODO:
        }
    }

    private fun feedScreen() {
        binding.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                parentImg.visibility = View.VISIBLE
            }, 2000)
            alpha()
        }
    }

    private fun alpha() {
        binding.apply {
            mainImg2.animate().alpha(0F).duration = 8000
            mainImg.animate().alpha(0.25f).duration = 4000
        }

    }


    private fun showOrNavigateToStoryDetails(storyId: Int, storyMusUrl: String) {

        val intent: Intent =
            Intent(this.context, StoryDetailsActivity::class.java).putExtra("storyId", storyId)
                .putExtra("storyMusUrl", storyMusUrl)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)


    }

}