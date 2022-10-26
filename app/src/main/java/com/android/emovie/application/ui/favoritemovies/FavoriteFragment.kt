package com.android.emovie.application.ui.favoritemovies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.emovie.R
import com.android.emovie.application.ui.Constants
import com.android.emovie.application.ui.Constants.Companion.FLOW_FAVORITES_MOVIES
import com.android.emovie.application.ui.common.MoviesAdapter
import com.android.emovie.application.ui.common.MoviesViewModel
import com.android.emovie.application.ui.common.OnItemClickListener
import com.android.emovie.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var binding: FragmentFavoritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
        binding.progressBarRecyclerView.visibility = View.VISIBLE
        binding.refreshFavoriteMovies.setOnRefreshListener {
            getFavoritesMovies()
            binding.refreshFavoriteMovies.isRefreshing = false
        }
        getFavoritesMovies()
    }

    private fun getFavoritesMovies() {
        viewModel.getFavoritesMovies.observe(viewLifecycleOwner) {
            with(binding) {
                if (it.isEmpty()) {
                    favoritesEmptyMessage.visibility = View.VISIBLE
                    moviesRecyclerView.visibility = View.GONE
                    progressBarRecyclerView.visibility = View.GONE
                } else {
                    moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
                    moviesRecyclerView.setHasFixedSize(true)
                    moviesRecyclerView.adapter = MoviesAdapter(
                        it.map { Pair(it.id, it.posterPath) },
                        object : OnItemClickListener {
                            override fun onItemClick(id: String) {
                                val bundle = Bundle().apply {
                                    putString(Constants.MOVIE_ID_KEY, id)
                                    putString(Constants.FLOW_KEY, FLOW_FAVORITES_MOVIES)
                                }
                                findNavController().navigate(R.id.movieDetailFragment, bundle)
                            }
                        })
                    favoritesEmptyMessage.visibility = View.GONE
                    progressBarRecyclerView.visibility = View.GONE
                    moviesRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}
