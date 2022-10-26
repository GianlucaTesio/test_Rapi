package com.android.emovie.application.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.emovie.R
import com.android.emovie.application.ui.Constants.Companion.FLOW_KEY
import com.android.emovie.application.ui.Constants.Companion.GLIDE_PLACEHOLDER_MOVIE
import com.android.emovie.application.ui.Constants.Companion.MOVIES_FLOW
import com.android.emovie.data.model.Movie
import com.android.emovie.application.ui.Constants.Companion.MOVIE_ID_KEY
import kotlinx.android.synthetic.main.movies_item_recycler_view.view.*

class PagingMovieAdapter(private val fragment: Fragment) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(
    MovieDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movies_item_recycler_view, parent, false)
            return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as MovieViewHolder).bind(it, fragment)}
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.movies_item_recycler_view

    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, fragment: Fragment) {
            itemView.imageViewItemRecyclerView.load(movie.posterPath, GLIDE_PLACEHOLDER_MOVIE)
            itemView.setOnClickListener{
                val bundle = Bundle().apply {
                    putString(MOVIE_ID_KEY, movie.id)
                    putString(FLOW_KEY, MOVIES_FLOW)
                }
                fragment.findNavController().navigate(R.id.movieDetailFragment, bundle)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
