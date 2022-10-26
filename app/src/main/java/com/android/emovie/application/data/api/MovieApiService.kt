package com.android.emovie.application.data.api

import com.android.emovie.application.ui.Constants.Companion.APY_KEY
import com.android.emovie.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/{path}")
    suspend fun getMovies(
        @Path("path") path: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String? = APY_KEY
    ): Movies

    @GET("discover/movie")
    suspend fun discover(
        @Query("page") page: Int,
        @Query("year") releaseYear: Int? = null,
        @Query("language") language: String? = null,
        @Query("api_key") apiKey: String? = APY_KEY
    ): Movies

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String? = APY_KEY
    ): Cast

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String? = APY_KEY
    ): Movie

    @GET("person/{person_id}")
    suspend fun getActorDetails(
        @Path("person_id") personId: String,
        @Query("api_key") apiKey: String? = APY_KEY
    ): Actor

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String? = APY_KEY
    ): Videos
}
