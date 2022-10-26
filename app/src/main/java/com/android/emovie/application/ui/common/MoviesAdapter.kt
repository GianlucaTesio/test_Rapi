package com.android.emovie.application.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.emovie.application.ui.Constants
import com.android.emovie.databinding.MoviesItemRecyclerViewBinding

class MoviesAdapter(private val posterPaths: List<Pair<String, String?>>, private val listener: OnItemClickListener)
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MoviesItemRecyclerViewBinding.inflate(LayoutInflater
                .from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = posterPaths[position]
        viewHolder.bind(currentItem.second)
    }

    override fun getItemCount() = posterPaths.size


    inner class ViewHolder(
        private val binding: MoviesItemRecyclerViewBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = posterPaths[position]
                    listener.onItemClick(item.first)
                }
            }
        }
        fun bind(posterPath: String?) {
            with(binding) {
                imageViewItemRecyclerView.load(posterPath, Constants.GLIDE_PLACEHOLDER_MOVIE)
            }
        }
    }
}