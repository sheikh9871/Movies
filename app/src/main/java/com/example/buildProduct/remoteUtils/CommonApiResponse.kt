package com.example.buildProduct.remoteUtils

import com.example.buildProduct.movies.data.ResultsItem
import com.google.gson.annotations.SerializedName

data class CommonApiResponse<T>(

    @field:SerializedName("next")
    var next: String?,

    @field:SerializedName("page")
    var page: Int?,

    @field:SerializedName("results")
    val results: T? = null

) {
}