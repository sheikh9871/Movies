package com.example.buildProduct.movies.owner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.buildProduct.R
import com.example.buildProduct.binding.BindingComponent
import com.example.buildProduct.databinding.MovieDetailsLayoutBinding
import com.example.buildProduct.extensions.emptyViewWithError
import com.example.buildProduct.extensions.tryOrNull
import com.example.buildProduct.helpers.autoCleared
import com.example.buildProduct.movies.observer.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment: Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()

    private var binding by autoCleared<MovieDetailsLayoutBinding>()

    private var movieId: String? = "-1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = tryOrNull {
            DataBindingUtil.inflate(
                inflater,
                R.layout.movie_details_layout,
                container,
                false,
                BindingComponent
            )
        } ?: return emptyViewWithError()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_MOVIE_ID, movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = savedInstanceState?.getString(KEY_MOVIE_ID) ?: arguments?.getString(KEY_MOVIE_ID)

        binding.viewModel = viewModel
        movieId?.let {id->
            viewModel.fetchMovieDetails(id).observe(viewLifecycleOwner){
                viewModel.movie = it
                viewModel.notifyChange()
            }
        }

    }

    companion object {
        const val KEY_MOVIE_ID = "movieId"
    }
}