package com.quakes.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.quakes.api.QuakesApi
import com.quakes.model.Earthquake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class QuakesDataSource(
    private val scope: CoroutineScope,
    private val service: QuakesApi
) : PageKeyedDataSource<Int, Earthquake>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Earthquake>
    ) {
        scope.launch {
            try {
                val response = service.getQuakes(page = 1, perPage = params.requestedLoadSize)
                if (response.isSuccessful) {
                    response.body()?.let { quakes ->
                        quakes.earthquakes?.let {
                            callback.onResult(it, null, 2)
                            networkState.postValue(NetworkState.Success)
                        }
                    }
                } else {
                    networkState.postValue(NetworkState.Failed)
                }
            } catch (exception: Exception) {
                Log.e("QuakesDataSource", "Failed to fetch data: ${exception.message}")
                networkState.postValue(NetworkState.Failed)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Earthquake>) {
        scope.launch {
            try {
                val page = params.key
                val response = service.getQuakes(page = 1, perPage = params.requestedLoadSize)
                if (response.isSuccessful) {
                    response.body()?.let { quakes ->
                        quakes.earthquakes?.let {
                            callback.onResult(it, page + 1)
                        }
                    }
                } else {
                    networkState.postValue(NetworkState.Failed)
                }
            } catch (exception: Exception) {
                Log.e("QuakesDataSource", "Failed to fetch data: ${exception.message}")
                networkState.postValue(NetworkState.Failed)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Earthquake>
    ) {
        //Not required in this case
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}