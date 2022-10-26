package com.android.emovie.application.ui.common

import android.widget.ImageView
import com.android.emovie.R
import com.android.emovie.application.ui.Constants
import com.android.emovie.application.ui.Constants.Companion.GLIDE_PLACEHOLDER_ACTOR
import com.android.emovie.application.ui.Constants.Companion.GLIDE_PLACEHOLDER_MOVIE
import com.android.emovie.application.ui.Constants.Companion.POSTER_BASE_URL
import com.bumptech.glide.Glide

fun ImageView.load(url: String?, placeholder: String) {
    val placeholderId = when(placeholder) {
        GLIDE_PLACEHOLDER_MOVIE -> R.drawable.image_default_movie
        GLIDE_PLACEHOLDER_ACTOR -> R.drawable.image_default_actor
        else -> 0
    }

    url?.let {
        Glide.with(context)
            .load(POSTER_BASE_URL + it)
            .placeholder(placeholderId)
            .into(this)
    }
}
