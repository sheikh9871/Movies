package com.example.buildProduct.pagingUtils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.buildProduct.remoteUtils.Status

data class Listing<T : Any, R>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val networkState: LiveData<Status>,
    // represents the refresh status to show to the user. Separate from networkState, this
    // value is importantly only when refresh is requested.
    val refreshState: LiveData<Status>,
    // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit,
    // retries any failed requests.
    val retry: () -> Unit,
    // represents the complete api response.
    val resourceState: LiveData<R>
)