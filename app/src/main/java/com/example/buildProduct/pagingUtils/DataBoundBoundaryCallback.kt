package com.example.buildProduct.pagingUtils

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.PagingRequestHelper
import com.example.buildProduct.helpers.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class DataBoundBoundaryCallback<T : Any, R>(
    private val appExecutors: AppExecutors,
    private val uniqueValue: Long? = null,
    private val uniqueValueGetter: UniqueValueGetter? = null
) : PagedList.BoundaryCallback<T>() {

    val helper = PagingRequestHelper(appExecutors.diskIO())
    val networkState = helper.createStatusLiveData()
    private var _resourceState = MutableLiveData<R>()
    val resourceState: LiveData<R> = _resourceState

    private fun shouldCallApi(): Boolean {
        return uniqueValueGetter?.uniqueValue() == uniqueValue
    }

    @MainThread
    override fun onZeroItemsLoaded() {
        appExecutors.diskIO().execute {
            if (shouldCallApi())
                helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
                    zeroItemLoaded().enqueue(createWebserviceCallback(it))
                }
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: T) {
        appExecutors.diskIO().execute {
            if (shouldCallApi())
                helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
                    itemAtEndLoaded(itemAtEnd).enqueue(createWebserviceCallback(it))
                }
        }
    }

    protected abstract fun zeroItemLoaded(): Call<R>

    protected abstract fun itemAtEndLoaded(item: T): Call<R>

    protected abstract fun handleAPIResponse(response: R?)

    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback): Callback<R> {
        return object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) {
                if (uniqueValueGetter?.uniqueValue() == uniqueValue)
                    it.recordFailure(t)
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (uniqueValueGetter?.uniqueValue() == uniqueValue)
                    appExecutors.diskIO().execute {
                        _resourceState.postValue(response.body())
                        handleAPIResponse(response.body())
                        it.recordSuccess()
                    }
            }
        }
    }

    interface UniqueValueGetter {
        fun uniqueValue(): Long? = null
    }
}