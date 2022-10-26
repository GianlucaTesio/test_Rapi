package com.android.emovie.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.android.emovie.application.data.api.NetworkDataSource
import com.android.emovie.data.model.*
import com.android.emovie.application.data.db.LocalDataSource
import com.android.emovie.data.model.db.FavoriteMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : MoviesRepositoryInterface {

    override fun getMovies(path: String): LiveData<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPaging(networkDataSource, path) }
        ).liveData

    override suspend fun discover(page: Int, releaseYear: Int?, language: String?): Movies {
        return networkDataSource.discover(page, language, releaseYear)
    }

    override suspend fun getMovieDetails(movieId: String): Movie {
        return networkDataSource.getMovieDetails(movieId)
    }


    override suspend fun getActor(actorId: String): Actor {
        return networkDataSource.getActorDetails(actorId)
    }

    override suspend fun getLocalActor(actorId: String): com.android.emovie.data.model.db.Actor {
        return localDataSource.getActor(actorId)
    }

    override suspend fun getCredits(movieId: String): Cast {
        return networkDataSource.getCredits(movieId)
    }

    override suspend fun getLocalCast(idCast: String): List<com.android.emovie.data.model.db.Actor> {
        val actorIds = localDataSource.getActorFromMovie(idCast)
        return actorIds.map { a -> localDataSource.getActor(a) }
    }

    override suspend fun getVideos(movieId: String): Videos {
        return networkDataSource.getVideos(movieId)
    }

    override suspend fun checkFavorite(idMovie: String): Boolean {
        return localDataSource.checkFavorite(idMovie) > 0
    }

    override suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        return localDataSource.insertFavoriteMovies(favoriteMovie)
    }

    override suspend fun insertCastMovie(idMovie: String, actors: List<Actor>) {
        actors.forEach { x -> localDataSource.insertCast(com.android.emovie.data.model.db.Cast(idMovie, x.id)) }
        actors.forEach { x -> localDataSource.insertActor(x.getActorDb()) }
    }

    override fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>> {
        return localDataSource.getAllFavoriteMovies()
    }

    override suspend fun getFavoriteMovie(idMovie: String): FavoriteMovie {
        return localDataSource.getFavoriteMovie(idMovie)
    }

    override suspend fun removeFavoriteMovie(idMovie: String) {
        return localDataSource.removeFavorite(idMovie)
    }
}
