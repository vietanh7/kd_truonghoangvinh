package com.example.reliatest.data.remote

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reliatest.MainApplication
import com.example.reliatest.model.ObjectResponse
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.vo.Resource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class NetworkBoundResource<RequestType, ResultType>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val result = MutableLiveData<Resource<ResultType>>()

    fun build(): NetworkBoundResource<RequestType, ResultType> {
        PopupUtil.showLoading()

        CoroutineScope(dispatcher).launch {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    if (MainApplication.instance.isNetworkConnected()) {
                        fetchFromNetwork()
                    } else {
                        PopupUtil.showPopupError("No internet connection")
                    }
                } catch (e: Exception) {
                    PopupUtil.hideAllPopup()
                    setValue(Resource.Error(e.message ?: "Unknown Error", 404))
                }
            } else {
                dbResult?.let {
                    setValue(Resource.Success(dbResult))
                }
            }
        }
        return this
    }

    private suspend fun fetchFromNetwork() {
        val apiResponse = createCall()
        if (apiResponse.isSuccessful) {
            val body = apiResponse.body()
            when (apiResponse.code()) {
                200, 201 -> {
                    if (body == null) {
                        setValue(Resource.Success(null))
                    } else {
                        saveCallResult(body)
                        val result = loadFromDb() ?: processResponse(body)
                        setValue(Resource.Success(result))
                    }
                }
            }
        } else {
            val response =
                Gson().fromJson(apiResponse.errorBody()?.toString(), ObjectResponse::class.java)
            val errorMsg = response?.detail ?: ""
            setValue(Resource.Error(errorMsg, apiResponse.code()))
        }
        PopupUtil.hideAllPopup()
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType?

    // Called to save the result of the API response into the database
    @WorkerThread
    protected open suspend fun saveCallResult(item: RequestType) {
    }

    // Called with the data in the database to decide whether to fetch
// potentially updated data from the network.
    @MainThread
    protected open fun shouldFetch(data: ResultType?): Boolean = true

    // Called to get the cached data from the database.
    @MainThread
    protected open suspend fun loadFromDb(): ResultType? = null

    // Called to create the API call.
    @MainThread
    protected abstract suspend fun createCall(): Response<RequestType>

    // Called when the fetch fails. The child class may want to reset components
// like rate limiter.
    protected open fun onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
// in the base class.
    fun asLiveData(): LiveData<Resource<ResultType>> = result
}