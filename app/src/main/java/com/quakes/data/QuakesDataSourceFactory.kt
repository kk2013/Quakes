package com.quakes.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.quakes.api.QuakesApi
import com.quakes.model.Earthquake
import kotlinx.coroutines.CoroutineScope

class QuakesDataSourceFactory(
    private val scope: CoroutineScope,
    private val service: QuakesApi
) : DataSource.Factory<Int, Earthquake>() {

    val quakesLiveData = MutableLiveData<QuakesDataSource>()

    override fun create(): DataSource<Int, Earthquake> {
        val source = QuakesDataSource(scope, service)
        quakesLiveData.postValue(source)
        return source
    }
}