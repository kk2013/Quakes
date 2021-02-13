package com.quakes.quakeslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.quakes.data.NetworkState
import com.quakes.data.QuakesDataSourceFactory
import com.quakes.data.QuakesRepository
import com.quakes.model.Earthquake
import javax.inject.Inject

class QuakesListViewModel @Inject constructor(private val quakesRepo: QuakesRepository) :
    ViewModel() {

    private val dataSourceFactory: QuakesDataSourceFactory =
        quakesRepo.createQuakesDataSource(viewModelScope)

    var quakes: LiveData<PagedList<Earthquake>>
    var networkState: LiveData<NetworkState>

    init {
        this.quakes = quakesRepo.getQuakes(dataSourceFactory)
        networkState = switchMap(dataSourceFactory.quakesLiveData) { it.networkState }
    }
}