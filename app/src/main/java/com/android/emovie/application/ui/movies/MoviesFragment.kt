package com.android.emovie.application.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.android.emovie.R
import com.android.emovie.application.ui.Constants.Companion.NOW_PLAYING_MOVIES
import com.android.emovie.application.ui.Constants.Companion.POPULAR_MOVIES
import com.android.emovie.application.ui.Constants.Companion.TOP_RATED_MOVIES
import com.android.emovie.application.ui.Constants.Companion.UPCOMING_MOVIES
import com.android.emovie.application.ui.common.StatusFragment
import com.android.emovie.application.ui.nowplaying.NowPlayingFragment
import com.android.emovie.application.ui.popular.PopularFragment
import com.android.emovie.application.ui.toprated.TopRatedFragment
import com.android.emovie.application.ui.upcoming.UpcomingFragment
import com.android.emovie.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private var sections: MutableList<String> = mutableListOf()
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var statusFragments: StatusFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)
        sections = mutableListOf(NOW_PLAYING_MOVIES, UPCOMING_MOVIES, POPULAR_MOVIES, TOP_RATED_MOVIES)

        statusFragments = object : StatusFragment{
            override fun success(tag: String) {
                binding.moviesError.visibility = View.GONE
                if (!sections.contains(tag))
                    sections.add(tag)
            }

            override fun error(tag: String) {
                sections.remove(tag)
                if (sections.isEmpty()) {
                    binding.moviesError.visibility = View.VISIBLE
                }
            }
        }

        parentFragmentManager.addFragmentOnAttachListener { _, fragment ->
            when (fragment){
                is NowPlayingFragment -> fragment.attachStatusFragmentInterface(statusFragments)
                is UpcomingFragment -> fragment.attachStatusFragmentInterface(statusFragments)
                is PopularFragment -> fragment.attachStatusFragmentInterface(statusFragments)
                is TopRatedFragment -> fragment.attachStatusFragmentInterface(statusFragments)
            }
        }

        parentFragmentManager.commit {
            add<UpcomingFragment>(R.id.moviesContainer, UPCOMING_MOVIES)
            add<NowPlayingFragment>(R.id.moviesContainer, NOW_PLAYING_MOVIES)
            add<PopularFragment>(R.id.moviesContainer, POPULAR_MOVIES)
            add<TopRatedFragment>(R.id.moviesContainer, TOP_RATED_MOVIES)
        }

        binding.refreshMovies.setOnRefreshListener {
            getMovies()
            binding.refreshMovies.isRefreshing = false
        }
    }

    fun getMovies() {
        (parentFragmentManager.findFragmentByTag(NOW_PLAYING_MOVIES) as? NowPlayingFragment?)?.getMovies()
        (parentFragmentManager.findFragmentByTag(UPCOMING_MOVIES) as? UpcomingFragment?)?.getMovies()
        (parentFragmentManager.findFragmentByTag(POPULAR_MOVIES) as? PopularFragment?)?.getMovies()
        (parentFragmentManager.findFragmentByTag(TOP_RATED_MOVIES) as? TopRatedFragment?)?.getMovies()
    }
}
