package com.quakes.data

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.quakes.quakeslist.QuakesListViewModel
import org.junit.Before
import org.junit.Test

class QuakesViewModelTest {

    private var mockQuakesRepository: QuakesRepository = mock()
    private var mockDataSourceFactory: QuakesDataSourceFactory = mock()
    private var mockRepo: MutableLiveData<QuakesDataSource> = mock()

    private lateinit var quakesListViewModel: QuakesListViewModel

    @Before
    fun setUp() {
        whenever(mockQuakesRepository.createQuakesDataSource(any())).thenReturn(
            mockDataSourceFactory
        )
        whenever(mockDataSourceFactory.quakesLiveData).thenReturn(mockRepo)

        quakesListViewModel = QuakesListViewModel(mockQuakesRepository)
    }

    @Test
    fun `init`() {
        verify(mockQuakesRepository).createQuakesDataSource(any())
        verify(mockQuakesRepository).getQuakes(any())
    }
}