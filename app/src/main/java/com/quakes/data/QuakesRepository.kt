package com.quakes.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.quakes.api.QuakesApi
import com.quakes.model.Earthquake
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executors
import javax.inject.Inject

class QuakesRepository @Inject constructor(private val quakesApi: QuakesApi) {

    fun getQuakes(dataSourceFactory: QuakesDataSourceFactory): LiveData<PagedList<Earthquake>> {
        val pagedList = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_SIZE)
            .build()

        return LivePagedListBuilder(dataSourceFactory, pagedList)
            .setInitialLoadKey(1)
            .setFetchExecutor(Executors.newFixedThreadPool(3))
            .build()
    }

    fun createQuakesDataSource(scope: CoroutineScope): QuakesDataSourceFactory {
        return QuakesDataSourceFactory(scope, quakesApi)
    }

    companion object {
        const val PAGE_SIZE = 12
        const val PREFETCH_SIZE = 6
        const val INITIAL_LOAD_SIZE = 20
    }
}