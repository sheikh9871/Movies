package com.example.buildProduct.movies.observer

import androidx.lifecycle.LiveData
import com.example.buildProduct.base.BaseViewModel
import com.example.buildProduct.movies.data.Movie
import com.example.buildProduct.movies.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MovieRepository
): BaseViewModel() {

    var movie: Movie? = null

    fun fetchMovieDetails(id: String): LiveData<Movie> = repo.fetchMovieDetails(id)
}