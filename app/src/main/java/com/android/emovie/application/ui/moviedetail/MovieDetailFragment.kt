package com.android.emovie.application.ui.moviedetail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.emovie.R
import com.android.emovie.application.ui.Constants.Companion.FLOW_FAVORITES_MOVIES
import com.android.emovie.application.ui.Constants.Companion.FLOW_KEY
import com.android.emovie.application.ui.Constants.Companion.GLIDE_PLACEHOLDER_MOVIE
import com.android.emovie.application.ui.Constants.Companion.MOVIES_FLOW
import com.android.emovie.application.ui.common.load
import com.android.emovie.data.model.Movie
import com.android.emovie.application.ui.Constants.Companion.MOVIE_ID_KEY
import com.android.emovie.application.ui.Constants.Companion.PERSON_ID_KEY
import com.android.emovie.application.ui.Constants.Companion.VIDEO_KEY
import com.android.emovie.application.ui.common.OnItemClickListener
import com.android.emovie.application.ui.videotrailer.VideoTrailerActivity
import com.android.emovie.data.model.Actor
import com.android.emovie.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val viewModel by viewModels<MovieDetailViewModel>()
    private lateinit var castAdapter: CastAdapter
    private lateinit var binding: FragmentMovieDetailBinding
    private var castMovie: List<Actor>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailBinding.bind(view)
        arguments?.getString(MOVIE_ID_KEY)?.run {

            if (arguments?.getString(FLOW_KEY) == MOVIES_FLOW) {
                viewModel.getMovieById(this).observe(viewLifecycleOwner) { response ->
                    bindView(response)
                }

                viewModel.getCast(this).observe(viewLifecycleOwner) { response ->
                    castMovie = response?.cast
                    bindCastView(MOVIES_FLOW)
                }
            }

            if (arguments?.getString(FLOW_KEY) == FLOW_FAVORITES_MOVIES) {
                viewModel.getFavoritesMovie(this).observe(viewLifecycleOwner) { response ->
                    bindView(response)
                }
                viewModel.getLocalCast(this).observe(viewLifecycleOwner) { response ->
                    castMovie = response
                    bindCastView(FLOW_FAVORITES_MOVIES)
                }
            }

            viewModel.checkMovie(this).observe(viewLifecycleOwner) { exists ->
                binding.movieDetailsToggleFavorite.isChecked = exists
            }

            viewModel.getVideos(this).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    binding.buttonVideoTrailer.setOnClickListener {
                        val intent = Intent(activity, VideoTrailerActivity::class.java)
                        intent.putExtra(VIDEO_KEY, response.results.first().key)
                        startActivity(intent)
                    }
                } else {
                    binding.buttonVideoTrailer.visibility = View.GONE
                }
            }
        }
    }

    private fun bindCastView(flow: String) {
        if (castMovie != null) {
            castAdapter = CastAdapter(castMovie!!, object : OnItemClickListener {
                override fun onItemClick(id: String) {
                    val bundle = Bundle().apply {
                        putString(PERSON_ID_KEY, id)
                        putString(FLOW_KEY, flow)
                    }
                    findNavController().navigate(R.id.actorDetailFragment, bundle)
                }
            })
            binding.movieCastRecyclerView.run {
                layoutManager = GridLayoutManager(context, 3)
                setHasFixedSize(true)
                adapter = castAdapter
            }
        } else {
            binding.movieCastRecyclerView.visibility = View.GONE
        }
    }

    private fun bindView(movieDetail: Movie?) {
        movieDetail?.let {
            binding.movieDetailImageView.load(movieDetail.posterPath, GLIDE_PLACEHOLDER_MOVIE)
            binding.movieDetailTitle.text = it.title

            it.releaseDate?.let { releaseDate -> binding.movieDetailDate.text = releaseDate }
                ?: run { (binding.movieDetailDate.parent as LinearLayout).visibility = View.GONE }

            it.voteAverage?.let { voteAverage ->
                binding.movieDetailRating.text = voteAverage.toString()
            } ?: run { (binding.movieDetailRating.parent as LinearLayout).visibility = View.GONE }

            it.runtime?.let {
                binding.movieDetailRuntime.text = getString(R.string.movie_detail_minutes, it)
            } ?: run {
                (binding.movieDetailRuntime.parent as LinearLayout).visibility = View.GONE
            }

            it.overview?.let { overview -> binding.movieDetailOverview.text = overview } ?: run {
                (binding.movieDetailOverview.parent as LinearLayout).visibility = View.GONE
            }

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            it.budget?.let { budget ->
                binding.movieDetailBudget.text = formatCurrency.format(budget)
            } ?: run { (binding.movieDetailBudget.parent as LinearLayout).visibility = View.GONE }

            it.revenue?.let { revenue ->
                binding.movieDetailRevenue.text = formatCurrency.format(revenue)
            } ?: run {
                (binding.movieDetailRevenue.parent as LinearLayout).visibility = View.GONE
            }

            binding.movieDetailsToggleFavorite.setOnClickListener {
                if (binding.movieDetailsToggleFavorite.isChecked) {
                    viewModel.addToFavorite(movieDetail)
                    castMovie?.let { actorList ->
                        viewModel.addLocalCast(movieDetail.id, actorList)
                    }
                } else {
                    viewModel.removeFromFavorite(movieDetail.id)
                }
            }
            binding.movieDetailProgressBar.visibility = View.GONE
            binding.movieDetailTextError.visibility = View.GONE
        } ?: kotlin.run {
            binding.movieDetailTextError.visibility = View.VISIBLE
            binding.movieDetailProgressBar.visibility = View.GONE
        }
    }
}
