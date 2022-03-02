package com.example.reliatest.data.remote

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reliatest.MainApplication
import com.example.reliatest.utils.PopupUtil
import com.example.reliatest.vo.ReliaResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class ReliaNetworkBoundResource<ResponseType>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val result = MutableLiveData<ReliaResource<ResponseType>>()

    fun build(): ReliaNetworkBoundResource<ResponseType> {
        PopupUtil.showLoading()

        CoroutineScope(dispatcher).launch {
            if (MainApplication.instance.isNetworkConnected()) {
                try {
                    fetchFromNetwork()
                } catch (e: Exception) {
                    PopupUtil.hideAllPopup()
                    PopupUtil.showPopupError(e.message ?: "Unknown Error")
                }
            } else {
                PopupUtil.showPopupError("No internet connection")
            }
        }
        return this
    }

    private suspend fun fetchFromNetwork() {
        val apiResponse = createCall()
        if (apiResponse.isSuccessful) {
            val body = apiResponse.body()
            setValue(ReliaResource.Success(body))
            PopupUtil.hideAllPopup()
        } else {
            PopupUtil.showPopupError("${apiResponse.message()} (Code: ${apiResponse.code()})")
        }
    }

    @MainThread
    private fun setValue(newValue: ReliaResource<ResponseType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    // Called to create the API call.
    @MainThread
    protected abstract suspend fun createCall(): Response<ResponseType>


    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun asLiveData(): LiveData<ReliaResource<ResponseType>> = result
}