package com.android.emovie.application.data.db

import androidx.lifecycle.LiveData
import com.android.emovie.data.model.db.Actor
import com.android.emovie.data.model.db.Cast
import com.android.emovie.data.model.db.FavoriteMovie
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val newsDAO: MoviesDAO) {

    suspend fun insertFavoriteMovies(favoriteMovie: FavoriteMovie) {
        newsDAO.insertFavoriteMovies(favoriteMovie)
    }

    suspend fun insertActor(actor: Actor) {
        newsDAO.insertActor(actor)
    }

    suspend fun insertCast(cast: Cast) {
        newsDAO.insertCast(cast)
    }

    suspend fun checkFavorite(idMovie: String): Int {
        return newsDAO.checkFavoriteExists(idMovie)
    }

    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return newsDAO.getAllFavorites()
    }

    suspend fun getActor(idActor: String): Actor {
        return newsDAO.getActor(idActor)
    }

    suspend fun getActorFromMovie(idCast: String): List<String> {
        return newsDAO.getActorFromMovie(idCast)
    }

    suspend fun getFavoriteMovie(idMovie: String): FavoriteMovie {
        return newsDAO.getFavoriteMovie(idMovie)
    }

    suspend fun removeFavorite(id: String) {
        newsDAO.removeFavorite(id)
    }
}
