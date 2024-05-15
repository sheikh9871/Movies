package com.example.buildProduct.movies.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.buildProduct.pagingUtils.PaginationCallDecider
import com.google.gson.annotations.SerializedName
import dagger.Provides

@Entity(tableName = "Movie")
data class Movie(

    @PrimaryKey
    @field:SerializedName("id")
    var id: String = "-1",

    var title: String? = null,

    var url: String? = null,

    var plotText: String? = null,

    var genresText: String? = "",

    @field:SerializedName("day")
    var day: String? = null,

    @field:SerializedName("month")
    var month: String? = null,

    @field:SerializedName("year")
    var year: String? = null,

    @Ignore
    @field:SerializedName("titleType")
    var titleType: TitleType? = null,

    @Ignore
    @field:SerializedName("primaryImage")
    var primaryImage: PrimaryImage? = null,

    @Ignore
    @field:SerializedName("releaseDate")
    var releaseDate: ReleaseDate? = null,

    @Ignore
    @field:SerializedName("originalTitleText")
    var originalTitleText: OriginalTitleText? = null,

    @Ignore
    @field:SerializedName("titleText")
    var titleText: TitleText? = null,

    @Ignore
    @field:SerializedName("releaseYear")
    var releaseYear: ReleaseYear? = null,

    @Ignore
    @field:SerializedName("plot")
    var plot: Plot? = null,

    @Ignore
    @field:SerializedName("genres")
    var genres: Genres? = null,

    var nextCursor: Int? = -1
) : PaginationCallDecider {

    override fun shouldCall(): Boolean {
        return nextCursor != null && nextCursor != -1
    }

    fun concatenateDate(): String {
        return "$day/$month/$year"
    }
}
