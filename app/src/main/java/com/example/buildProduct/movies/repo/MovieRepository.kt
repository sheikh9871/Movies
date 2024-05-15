package com.example.buildProduct.movies.repo

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.buildProduct.helpers.AppExecutors
import com.example.buildProduct.movies.data.Movie
import com.example.buildProduct.movies.data.MoviesDao
import com.example.buildProduct.pagingUtils.DataBoundBoundaryCallback
import com.example.buildProduct.pagingUtils.Listing
import com.example.buildProduct.pagingUtils.PaginationRepository
import com.example.buildProduct.remoteUtils.CommonApiResponse
import com.example.buildProduct.remoteUtils.UrlHub
import com.example.buildProduct.remoteUtils.WebService
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val pagedListConfig: PagedList.Config,
    private val executors: AppExecutors,
    private val webService: WebService,
    private val dao: MoviesDao
) : PaginationRepository<Movie, CommonApiResponse<List<Movie>>>(
    executors = executors, pagedListConfig = pagedListConfig
) {

    @MainThread
    fun fetchMoviesList(): Listing<Movie, CommonApiResponse<List<Movie>>> {
        deleteMovieList()
        return response("")
    }

    private fun deleteMovieList() {
        executors.diskIO().execute {
            dao.deleteMovieList()
        }
    }

    override fun refreshAPI(search: String?): Call<CommonApiResponse<List<Movie>>> {
        throw Exception("Not implemented")
    }

    override fun boundaryCallback(search: String?): DataBoundBoundaryCallback<Movie, CommonApiResponse<List<Movie>>> {
        return MoviesListBoundaryCallback(
            appExecutors = executors,
            webService = webService,
            url = UrlHub.URL_FETCH_MOVIES_LIST,
            handleResponse = this::insertResultIntoDb,
        )
    }

    override fun dataSourceFactory(search: String?): DataSource.Factory<Int, Movie> {
        return dao.fetchMoviesList()
    }

    override fun refreshOperation(response: CommonApiResponse<List<Movie>>?) {
    }

    private fun insertResultIntoDb(response: CommonApiResponse<List<Movie>>?) {
        response?.let {
            dao.insertMovieListData(response = response)
        }
    }

    fun fetchMovieDetails(id: String): LiveData<Movie>  = dao.fetchMovieDetails(id)
}