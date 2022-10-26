package com.android.emovie.application.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.android.emovie.data.model.Movie
import com.android.emovie.data.repositories.MoviesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val mainRepository: MoviesRepositoryInterface) :
    ViewModel() {

    fun getMovieById(idMovie: String) = liveData(Dispatchers.IO) {
        try {
            emit(mainRepository.getMovieDetails(idMovie))
        } catch (ignore: Exception) {
            emit(null)
        }
    }

    fun checkMovie(id: String)=  liveData(Dispatchers.IO) {
        emit(mainRepository.checkFavorite(id))
    }

    fun addToFavorite(favoriteMovie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.insertFavoriteMovie(
                favoriteMovie.getMovieDb()
            )
        }
    }

    fun addLocalCast(idMovie: String, actors : List<com.android.emovie.data.model.Actor>) {
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.insertCastMovie(idMovie, actors)
        }
    }

    fun removeFromFavorite(idMovie: String) {
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.removeFavoriteMovie(idMovie)
        }
    }

    fun getFavoritesMovie(id: String)=  liveData(Dispatchers.IO) {
        emit(mainRepository.getFavoriteMovie(id).getMovie())
    }

    fun getLocalCast(id: String) = liveData(Dispatchers.IO) {
        emit(mainRepository.getLocalCast(id).map { r -> r.getActor()})
    }

    fun getCast(idMovie: String) = liveData(Dispatchers.IO) {
        try {
            emit(mainRepository.getCredits(idMovie))
        } catch (ignore: Exception) {
            emit(null)
        }
    }

    fun getVideos(idMovie: String) = liveData(Dispatchers.IO) {
        try {
            emit(mainRepository.getVideos(idMovie))
        } catch (ignore: Exception) {
            emit(null)
        }
    }
}
