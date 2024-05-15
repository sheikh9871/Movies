package com.example.buildProduct.movies.observer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.buildProduct.base.BaseViewModel
import com.example.buildProduct.movies.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repo: MovieRepository
): BaseViewModel() {

    private val fetchCall = MutableLiveData<String>()

    private val repoResult = Transformations.map(fetchCall) {
        repo.fetchMoviesList()
    }

    val movieList = Transformations.switchMap(repoResult) { it?.pagedList }

    val networkState = Transformations.switchMap(repoResult) { it?.networkState }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }

    init {
        fetchCall.value = "fetch"
    }
}