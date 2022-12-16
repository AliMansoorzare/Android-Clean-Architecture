package serat.maemaeen.mahdavistories.favorite

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import serat.maemaeen.mahdavistories.adapter.FavAdapter
import serat.maemaeen.mahdavistories.adapter.StoryAdapter
import serat.maemaeen.mahdavistories.base.BaseFragment
import serat.maemaeen.mahdavistories.databinding.FragmentFavoritesBinding
import serat.maemaeen.mahdavistories.storyDetails.StoryDetailsActivity
import serat.maemaeen.mahdavistories.util.GlideLoading
import serat.maemaeen.mahdavistories.util.hide
import serat.maemaeen.mahdavistories.util.show
import javax.inject.Inject


class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoriteSotriesViewModel>() {

    @Inject
    lateinit var factory: FavoriteSotriesViewModel.Factory

    @Inject
    lateinit var glideLoading: GlideLoading
    private val favAdapter by lazy {
        FavAdapter(glideLoading, viewModel::onStoryClicked)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentFavoritesBinding =
        FragmentFavoritesBinding.inflate(inflater)

    override fun createViewModel(): FavoriteSotriesViewModel =
        ViewModelProvider(this, factory).get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun setupViews() {
        setupRecyclerView()
        binding.mainImg.animate().alpha(0.25f).duration = 4000

    }

    private fun setupRecyclerView() = with(binding.favFragmentRecycler) {
        layoutManager = GridLayoutManager(this.context,2)


    }

    private fun setupObservers() = with(viewModel) {
        getFavoriteUiState().observe { handleFavoriteUiState(it) }
        getNavigateState().observe { handleNavigationState(it) }
    }

    private fun handleFavoriteUiState(favoriteUiState: FavoriteSotriesViewModel.FavoriteUiState) =
        with(favoriteUiState) {
            if (isLoading) {
                binding.progressBar.show()

            } else {
                if (noDataAvailable) binding.noData.show() else binding.noData.hide()
                binding.progressBar.hide()
                favAdapter.setData(stories)
                binding.favFragmentRecycler.adapter = favAdapter


            }
        }

    private fun handleNavigationState(navigationState: FavoriteSotriesViewModel.NavigationState) =
        when (navigationState) {
            is FavoriteSotriesViewModel.NavigationState.StoriesDetails -> navigateToStoryDetails(
                navigationState.storyId
            )
        }

    private fun navigateToStoryDetails(storyId: Int) {
        startActivity(
            Intent(this.context, StoryDetailsActivity::class.java).putExtra(
                "storyId",
                storyId
            )
        )
    }


}