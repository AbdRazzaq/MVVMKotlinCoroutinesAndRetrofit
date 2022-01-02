package com.example.mvvmkotlincoroutinesandretrofit.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlincoroutinesandretrofit.data.model.MovieModel
import com.example.mvvmkotlincoroutinesandretrofit.data.network.MainRepository
import kotlinx.coroutines.*

class MoviesViewModel constructor(private val mainRepository: MainRepository):ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<MovieModel>>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}