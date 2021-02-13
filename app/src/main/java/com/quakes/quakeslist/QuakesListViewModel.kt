package com.quakes.quakeslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.quakes.api.QuakesApi
import com.quakes.data.QuakesDataSource
import com.quakes.model.Earthquake
import javax.inject.Inject

class QuakesListViewModel @Inject constructor(private val quakessApi: QuakesApi) : ViewModel() {

    var quakesLiveData: LiveData<PagedList<Earthquake>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        quakesLiveData = initializedPagedListBuilder(config).build()
    }

    fun getQuakes(): LiveData<PagedList<Earthquake>> = quakesLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Earthquake> {

        val dataSourceFactory = object : DataSource.Factory<Int, Earthquake>() {
            override fun create(): DataSource<Int, Earthquake> {
                return QuakesDataSource(viewModelScope, quakessApi)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}