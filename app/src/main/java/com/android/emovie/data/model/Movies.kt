package com.android.emovie.data.model

import com.google.gson.annotations.SerializedName

data class Movies(
    val page: Int,
    @SerializedName("total_pages")val totalPages: Int,
    @SerializedName("total_results")val totalResults: Int,
    val results: List<Movie>
)
