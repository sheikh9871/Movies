package com.example.buildProduct.pagingUtils

import com.example.buildProduct.helpers.AppExecutors

abstract class DataBoundBoundaryCallbackNew<T : PaginationCallDecider, R>(
    appExecutors: AppExecutors
) : DataBoundBoundaryCallback<T, R>(appExecutors) {

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        if (itemAtEnd.shouldCall())
            super.onItemAtEndLoaded(itemAtEnd)
    }

}