package com.android.emovie.data.model

import com.android.emovie.data.model.db.FavoriteMovie
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: String,
    val title: String,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    val revenue: Long?,
    val runtime: Int?,
    val budget: Long?
) {
    fun getMovieDb(): FavoriteMovie {
        return FavoriteMovie(
            id,
            title,
            releaseDate,
            voteAverage,
            runtime,
            overview,
            budget,
            revenue,
            posterPath
        )
    }
}
