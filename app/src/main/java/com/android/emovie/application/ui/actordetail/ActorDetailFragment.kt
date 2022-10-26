package com.android.emovie.application.ui.actordetail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.emovie.R
import com.android.emovie.application.ui.Constants.Companion.FLOW_FAVORITES_MOVIES
import com.android.emovie.application.ui.Constants.Companion.FLOW_KEY
import com.android.emovie.application.ui.Constants.Companion.GLIDE_PLACEHOLDER_ACTOR
import com.android.emovie.application.ui.Constants.Companion.MOVIES_FLOW
import com.android.emovie.data.model.Actor
import com.android.emovie.application.ui.Constants.Companion.PERSON_ID_KEY
import com.android.emovie.databinding.FragmentActorDetailBinding
import com.android.emovie.application.ui.common.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailFragment :Fragment(R.layout.fragment_actor_detail) {

    private val viewModel by viewModels<ActorDetailViewModel>()
    private lateinit var binding: FragmentActorDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentActorDetailBinding.bind(view)
        arguments?.getString(PERSON_ID_KEY)?.run {
            if (arguments?.getString(FLOW_KEY) == MOVIES_FLOW) {
                viewModel.getActor(this).observe(viewLifecycleOwner) { response ->
                    bindView(response)
                }
            }

            if (arguments?.getString(FLOW_KEY) == FLOW_FAVORITES_MOVIES) {
                viewModel.getLocalActor(this).observe(viewLifecycleOwner) { response ->
                    bindView(response)
                }
            }
        }
    }

    private fun bindView(actor: Actor?) {
        if (actor != null) {
            binding.actorDetailProgressBar.visibility = View.GONE
            binding.actorDetailTextError.visibility = View.GONE

            binding.actorDetailImageView.load(actor.profilePath, GLIDE_PLACEHOLDER_ACTOR)

            actor.name?.let { name -> binding.actorDetailName.text = name }
                ?: run {(binding.actorDetailName.parent as LinearLayout).visibility = View.GONE}

            actor.biography?.let { biography -> binding.actorDetailBiography.text = biography }
                ?: run {(binding.actorDetailBiography.parent as LinearLayout).visibility = View.GONE}

            actor.birthday?.let { birthday -> binding.actorDetailBirthday.text = birthday }
                ?: run {(binding.actorDetailBirthday.parent as LinearLayout).visibility = View.GONE}

            actor.popularity?.let { popularity -> binding.acotrDetailPopularity.text = popularity.toString() }
                ?: run {(binding.acotrDetailPopularity.parent as LinearLayout).visibility = View.GONE}

            actor.placeOfBirth?.let { placeOfBirth -> binding.actorDetailPlaceOfBirth.text = placeOfBirth }
                ?: run {(binding.actorDetailPlaceOfBirth.parent as LinearLayout).visibility = View.GONE}

            actor.homepage?.let { homepage ->
            binding.actorDetailHomepage.text = homepage
                binding.actorDetailHomepage.movementMethod = LinkMovementMethod.getInstance()
            }  ?: run {(binding.actorDetailHomepage.parent as LinearLayout).visibility = View.GONE}
        } else {
            binding.actorDetailProgressBar.visibility = View.GONE
            binding.actorDetailTextError.visibility = View.VISIBLE
        }
    }
}
