package com.android.emovie.application.data.api

import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: MovieApiService) {

    suspend fun getMovies(path: String, page: Int) = apiService.getMovies(path, page)

    suspend fun discover(page: Int, language: String?, releaseYear: Int?) = apiService.discover(page, releaseYear, language)

    suspend fun getActorDetails(movieId: String) = apiService.getActorDetails(movieId)

    suspend fun getCredits(movieId: String) = apiService.getCredits(movieId)

    suspend fun getMovieDetails(movieId: String) = apiService.getMovieDetails(movieId)

    suspend fun getVideos(movieId: String) = apiService.getVideos(movieId)

}
