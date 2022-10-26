package com.android.emovie.application.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.emovie.data.model.db.Actor
import com.android.emovie.data.model.db.Cast
import com.android.emovie.data.model.db.FavoriteMovie

@Dao
interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovies(favoriteMovie: FavoriteMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: Actor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCast(cast: Cast)

    @Query("SELECT count(*) FROM favorite_movies WHERE favorite_movies.id = :id")
    suspend fun checkFavoriteExists(id: String): Int

    @Query("SELECT * FROM favorite_movies WHERE favorite_movies.id = :id")
    suspend fun getFavoriteMovie(id: String): FavoriteMovie

    @Query("SELECT * FROM actors WHERE actors.id = :id")
    suspend fun getActor(id: String): Actor

    @Query("SELECT idActor FROM cast_movie WHERE cast_movie.id = :id")
    suspend fun getActorFromMovie(id: String): List<String>

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): LiveData<List<FavoriteMovie>>

    @Query("DELETE FROM favorite_movies WHERE favorite_movies.id = :id")
    suspend fun removeFavorite(id: String): Int
}
