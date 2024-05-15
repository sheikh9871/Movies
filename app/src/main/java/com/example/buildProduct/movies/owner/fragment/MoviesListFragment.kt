package com.example.buildProduct.movies.owner.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.buildProduct.extensions.emptyViewWithError
import com.example.buildProduct.extensions.tryOrNull
import com.example.buildProduct.helpers.AppExecutors
import com.example.buildProduct.helpers.autoCleared
import com.example.buildProduct.movies.observer.MovieListViewModel
import com.example.buildProduct.movies.owner.adapter.MovieListAdapter
import com.example.buildProduct.R
import com.example.buildProduct.binding.BindingComponent
import com.example.buildProduct.binding.FragmentBindingAdapters
import com.example.buildProduct.databinding.MovieListLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListFragment: Fragment() {

    private val viewModel: MovieListViewModel by viewModels()
    private var binding by autoCleared<MovieListLayoutBinding>()
    private var adapter by autoCleared<MovieListAdapter>()

    @Inject
    lateinit var executors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = tryOrNull {
            DataBindingUtil.inflate(
                inflater,
                R.layout.movie_list_layout,
                container,
                false,
                BindingComponent
            )
        } ?: return emptyViewWithError()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialisePaginationAdapter()
        observeMusicList()
    }

    private fun initialisePaginationAdapter() {
        adapter = MovieListAdapter(appExecutors = executors, dataBindingComponent = BindingComponent){action, item ->

            when (action){
                ACTION_DETAIL -> {
                    val bundle = Bundle()
                    bundle.putString("movieId", item?.id)
                    findNavController().navigate(R.id.movie_detail_fragment, bundle)

                }
                ACTION_RETRY -> { viewModel.retry() }
            }
        }.also {
            binding.moviesRecycler.adapter = it
        }
    }

    private fun observeMusicList() {
        viewModel.movieList.observe(viewLifecycleOwner) { pagedList ->
            adapter.submitList(pagedList)
            binding.listSize = pagedList.size
        }

        viewModel.networkState.observe(viewLifecycleOwner) { status ->
            binding.paginationStatus = status
            adapter.updateNetworkState(status)
        }
    }

    companion object {
        const val ACTION_RETRY = "retry"
        const val ACTION_DETAIL = "detail"
    }
}