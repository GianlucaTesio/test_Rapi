package com.android.emovie.application.ui.toprated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.emovie.R
import com.android.emovie.application.ui.Constants.Companion.FILTER_CURRENT_YEAR
import com.android.emovie.application.ui.Constants.Companion.FILTER_SPANISH
import com.android.emovie.application.ui.Constants.Companion.FLOW_KEY
import com.android.emovie.application.ui.Constants.Companion.MOVIES_FLOW
import com.android.emovie.application.ui.Constants.Companion.MOVIE_ID_KEY
import com.android.emovie.application.ui.Constants.Companion.TOP_RATED_MAX_SIZE_MOVIES
import com.android.emovie.application.ui.Constants.Companion.TOP_RATED_MOVIES
import com.android.emovie.application.ui.common.*
import com.android.emovie.databinding.FragmentTopRatedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment(R.layout.fragment_top_rated) {

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var binding: FragmentTopRatedBinding
    private lateinit var statusFragment: StatusFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopRatedBinding.bind(view)
        getMovies()
    }

    fun getMovies() {
        val releaseYear = if (binding.toggleButtonReleaseCurrentYear.isChecked) {
            binding.toggleButtonReleaseCurrentYear.setTextColor(R.color.black)
            FILTER_CURRENT_YEAR
        } else {
            binding.toggleButtonReleaseCurrentYear.setTextColor(R.color.white)
            null
        }
        val language = if (binding.toggleButtonSpanish.isChecked) {
            binding.toggleButtonSpanish.setTextColor(R.color.black)
            FILTER_SPANISH
        } else {
            binding.toggleButtonSpanish.setTextColor(R.color.white)
            null
        }
        discover(releaseYear, language)
    }

    fun attachStatusFragmentInterface(statusFragment: StatusFragment) {
        this.statusFragment = statusFragment
    }

    private fun discover(releaseYear: Int? = null, language: String? = null) {
        viewModel.discover(1, releaseYear = releaseYear, language = language)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Loading -> {
                        binding.moviesRecyclerView.visibility = View.GONE
                        binding.progressBarRecyclerView.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        this.view?.visibility = View.VISIBLE
                        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
                        binding.moviesRecyclerView.setHasFixedSize(true)
                        binding.moviesRecyclerView.adapter = MoviesAdapter(
                            response.data.results.take(TOP_RATED_MAX_SIZE_MOVIES)
                                .map { Pair(it.id, it.posterPath) },
                            object : OnItemClickListener {
                                override fun onItemClick(id: String) {
                                    val bundle = Bundle().apply {
                                        putString(MOVIE_ID_KEY, id)
                                        putString(FLOW_KEY, MOVIES_FLOW)
                                    }
                                    findNavController().navigate(R.id.movieDetailFragment, bundle)
                                }
                            })
                        statusFragment.success(TOP_RATED_MOVIES)
                        binding.progressBarRecyclerView.visibility = View.GONE
                        binding.moviesRecyclerView.visibility = View.VISIBLE
                    }
                    is Resource.Failure -> {
                        this.view?.visibility = View.GONE
                        statusFragment.error(TOP_RATED_MOVIES)
                    }
                }
                binding.toggleButtonSpanish.setOnCheckedChangeListener { _, _ ->
                    getMovies()
                }
                binding.toggleButtonReleaseCurrentYear.setOnCheckedChangeListener { _, _ ->
                    getMovies()
                }
            }
    }
}
