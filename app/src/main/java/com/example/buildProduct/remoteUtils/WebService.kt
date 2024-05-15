package com.example.buildProduct.remoteUtils

import com.example.buildProduct.movies.data.Movie
import com.example.buildProduct.movies.data.ResultsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WebService {

    @GET
    fun fetchMoviesList(
        @Url url: String,
        @Query("page") nextCursor: Int?,
        @Query("list") list: String?,
        @Query("info") info: String?
    ): Call<CommonApiResponse<List<Movie>>>
}