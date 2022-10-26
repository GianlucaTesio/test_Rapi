package com.android.emovie.application.ui.actordetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.android.emovie.data.repositories.MoviesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(private val mainRepository: MoviesRepositoryInterface) : ViewModel() {

    fun getActor(idMovie: String) = liveData(Dispatchers.IO) {
        try {
            emit(mainRepository.getActor(idMovie))
        } catch (ignore: Exception) {
            emit(null)
        }
    }

    fun getLocalActor(id: String) = liveData(Dispatchers.IO) {
        emit(mainRepository.getLocalActor(id).getActor())
    }
}
