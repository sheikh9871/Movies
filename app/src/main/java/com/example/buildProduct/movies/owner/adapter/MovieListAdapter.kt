package com.example.buildProduct.movies.owner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.buildProduct.helpers.AppExecutors
import com.example.buildProduct.movies.data.Movie
import com.example.buildProduct.movies.owner.fragment.MoviesListFragment.Companion.ACTION_DETAIL
import com.example.buildProduct.movies.owner.fragment.MoviesListFragment.Companion.ACTION_RETRY
import com.example.buildProduct.pagingUtils.DataBoundPagedListAdapter
import com.example.buildProduct.R
import com.example.buildProduct.databinding.MovieLayoutItemBinding

class MovieListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: (action: String?, item: Movie?) -> Unit,
) : DataBoundPagedListAdapter<Movie, MovieLayoutItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup): MovieLayoutItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_layout_item,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(
        binding: MovieLayoutItemBinding,
        item: Movie,
        position: Int
    ) {
        binding.data = item

        binding.root.setOnClickListener {
            callback.invoke(ACTION_DETAIL, item)
        }
    }

    override fun retryAction() {
        callback.invoke(ACTION_RETRY, null)
    }

}