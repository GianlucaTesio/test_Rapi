package com.android.emovie.application.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.emovie.application.ui.Constants
import com.android.emovie.application.ui.common.OnItemClickListener
import com.android.emovie.application.ui.common.load
import com.android.emovie.data.model.Actor
import com.android.emovie.databinding.CastItemRecyclerViewBinding

class CastAdapter(private val castList: List<Actor>, private val listener: OnItemClickListener)
    : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CastItemRecyclerViewBinding
                .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = castList[position]
        viewHolder.bind(currentItem)
    }

    override fun getItemCount() = castList.size

    inner class ViewHolder(
        private val binding: CastItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = castList[position]
                    listener.onItemClick(item.id)
                }
            }
        }
        fun bind(actor: Actor) {
            with(binding) {
                imageViewCastItemRecyclerView.load(actor.profilePath, Constants.GLIDE_PLACEHOLDER_ACTOR)
                textViewCastItemRecyclerView.text = actor.name
            }
        }
    }
}
