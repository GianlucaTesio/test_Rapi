package com.android.emovie.application.data.db

import androidx.lifecycle.LiveData
import com.android.emovie.data.model.db.Actor
import com.android.emovie.data.model.db.Cast
import com.android.emovie.data.model.db.FavoriteMovie
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val moviesDAO: MoviesDAO) {

    suspend fun insertFavoriteMovies(favoriteMovie: FavoriteMovie) {
        moviesDAO.insertFavoriteMovies(favoriteMovie)
    }

    suspend fun insertActor(actor: Actor) {
        moviesDAO.insertActor(actor)
    }

    suspend fun insertCast(cast: Cast) {
        moviesDAO.insertCast(cast)
    }

    suspend fun checkFavorite(idMovie: String): Int {
        return moviesDAO.checkFavoriteExists(idMovie)
    }

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return moviesDAO.getAllFavorites()
    }

    suspend fun getActor(idActor: String): Actor {
        return moviesDAO.getActor(idActor)
    }

    suspend fun getActorFromMovie(idCast: String): List<String> {
        return moviesDAO.getActorFromMovie(idCast)
    }

    suspend fun getFavoriteMovie(idMovie: String): FavoriteMovie {
        return moviesDAO.getFavoriteMovie(idMovie)
    }

    suspend fun removeFavorite(id: String) {
        moviesDAO.removeFavorite(id)
    }
}
