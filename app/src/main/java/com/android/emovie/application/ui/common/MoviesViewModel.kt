package com.android.emovie.application.ui.common

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.android.emovie.application.ui.Constants.Companion.TOP_RATED_MAX_SIZE_MOVIES
import com.android.emovie.data.repositories.MoviesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val mainRepository: MoviesRepositoryInterface) :
    ViewModel() {

    fun getMovies(path: String) = mainRepository.getMovies(path).cachedIn(viewModelScope)

    fun discover(page: Int, releaseYear: Int? = null, language: String? = null) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(Resource.Success(mainRepository.discover(page, releaseYear, language)))
            } catch (ignore: Exception) {
                emit(Resource.Failure)
            }
        }

    val getFavoritesMovies = mainRepository.getAllFavoriteMovies()
}
