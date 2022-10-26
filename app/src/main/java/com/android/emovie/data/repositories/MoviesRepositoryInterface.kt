package com.android.emovie.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.android.emovie.data.model.*
import com.android.emovie.data.model.db.FavoriteMovie

interface MoviesRepositoryInterface {

    fun getMovies(path: String): LiveData<PagingData<Movie>>

    suspend fun discover(page: Int, releaseYear: Int?, language: String?): Movies

    suspend fun getMovieDetails(movieId: String): Movie

    suspend fun getActor(actorId: String): Actor

    suspend fun getLocalActor(actorId: String): com.android.emovie.data.model.db.Actor

    suspend fun getCredits(movieId: String): Cast

    suspend fun getLocalCast(idCast: String): List<com.android.emovie.data.model.db.Actor>

    suspend fun getVideos(movieId: String): Videos

    suspend fun checkFavorite(idMovie: String): Boolean

    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie)

    suspend fun insertCastMovie(idMovie: String, actors: List<Actor>)

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>

    suspend fun getFavoriteMovie(idMovie: String): FavoriteMovie

    suspend fun removeFavoriteMovie(idMovie: String)
}
