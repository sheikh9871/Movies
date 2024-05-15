package com.example.buildProduct.movies.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.buildProduct.remoteUtils.CommonApiResponse

@Dao
interface MoviesDao {

    @Query("DELETE FROM Movie")
    fun deleteMovieList()

    @Query("SELECT * FROM Movie")
    fun fetchMoviesList(): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(list: List<Movie>)

    @Transaction
    fun insertMovieListData(response: CommonApiResponse<List<Movie>>) {
        val nextCursor = response.page?.plus(1)
        response.results?.let { list ->
            if (list.isEmpty()) {
                return
            }
            if (nextCursor != null)
                list.onEach { item ->
                    item.nextCursor = nextCursor
                    item.title = item.titleText?.text
                    item.url = item.primaryImage?.url
                    item.plotText = item.plot?.plotText?.plainText
                    item.day = item.releaseDate?.day
                    item.month = item.releaseDate?.month
                    item.year = item.releaseDate?.year
                    item.genres?.genres?.mapNotNull { it.text }?.joinToString(", ")?.let { item.genresText = it }

                }
            insertMoviesList(list)
        }
    }

    @Query("SELECT * FROM Movie WHERE id=:id")
    fun fetchMovieDetails(id: String): LiveData<Movie>

}