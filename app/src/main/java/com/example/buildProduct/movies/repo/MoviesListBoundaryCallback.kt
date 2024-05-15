package com.example.buildProduct.movies.repo

import com.example.buildProduct.helpers.AppExecutors
import com.example.buildProduct.movies.data.Movie
import com.example.buildProduct.pagingUtils.DataBoundBoundaryCallbackNew
import com.example.buildProduct.remoteUtils.CommonApiResponse
import com.example.buildProduct.remoteUtils.WebService
import retrofit2.Call

class MoviesListBoundaryCallback(
    appExecutors: AppExecutors,
    private val webService: WebService,
    private val url: String,
    private val handleResponse: (CommonApiResponse<List<Movie>>?) -> Unit,
) : DataBoundBoundaryCallbackNew<Movie, CommonApiResponse<List<Movie>>>(
    appExecutors
) {


    override fun zeroItemLoaded(): Call<CommonApiResponse<List<Movie>>> {
        return webService.fetchMoviesList(
            url = url,
            nextCursor = null,
            list = "top_rated_lowest_100",
            info = "base_info"
        )
    }

    override fun itemAtEndLoaded(item: Movie): Call<CommonApiResponse<List<Movie>>> {
        return webService.fetchMoviesList(
            url = url,
            nextCursor = item.nextCursor ?: -1,
            list = "top_rated_lowest_100",
            info = "base_info"
        )
    }

    override fun handleAPIResponse(response: CommonApiResponse<List<Movie>>?) {
        handleResponse(response)
    }

}