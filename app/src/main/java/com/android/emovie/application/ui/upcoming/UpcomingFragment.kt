package com.android.emovie.application.ui.upcoming

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.emovie.R
import com.android.emovie.application.ui.common.PagingMovieAdapter
import com.android.emovie.application.ui.Constants.Companion.UPCOMING_MOVIES
import com.android.emovie.application.ui.common.MoviesViewModel
import com.android.emovie.application.ui.common.StatusFragment
import com.android.emovie.databinding.FragmentUpcomingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var moviesAdapter: PagingMovieAdapter
    private lateinit var binding: FragmentUpcomingBinding
    private lateinit var statusFragment: StatusFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = PagingMovieAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpcomingBinding.bind(view)
        binding. moviesRecyclerView.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = moviesAdapter.apply {
                addLoadStateListener { loadState ->
                    if (loadState.source.refresh is LoadState.Error ||
                        (loadState.append.endOfPaginationReached
                                && loadState.source.refresh is LoadState.NotLoading && moviesAdapter.itemCount < 1)) {
                        statusFragment.error(UPCOMING_MOVIES)
                        this@UpcomingFragment.view?.visibility = View.GONE
                    } else {
                        this@UpcomingFragment.view?.visibility = View.VISIBLE
                        statusFragment.success(UPCOMING_MOVIES)
                        binding.moviesRecyclerView.visibility = View.VISIBLE
                        binding.progressBarRecyclerView.visibility = View.GONE
                    }
                }
            }
        }
        getMovies()
    }

    fun attachStatusFragmentInterface(statusFragment: StatusFragment) {
        this.statusFragment = statusFragment
    }

    fun getMovies() {
        binding.progressBarRecyclerView.visibility = View.VISIBLE
        binding.progressBarRecyclerView.visibility = View.GONE
        viewModel.getMovies(UPCOMING_MOVIES).observe(viewLifecycleOwner) { response ->
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle, response)
        }
    }
}
