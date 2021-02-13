package com.quakes.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PageKeyedDataSource
import com.nhaarman.mockito_kotlin.*
import com.quakes.api.QuakesApi
import com.quakes.model.Earthquake
import com.quakes.model.Earthquakes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class QuakesDataSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private var mockInitialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Earthquake> = mock()
    private var mockCallback: PageKeyedDataSource.LoadCallback<Int, Earthquake> = mock()
    private var mockService: QuakesApi = mock()
    private var mockHttpException: HttpException = mock()
    private var mockResponse: Response<Earthquakes> = mock()
    private var quake: Earthquake = mock()

    private val mockInitialParams: PageKeyedDataSource.LoadInitialParams<Int> =
        PageKeyedDataSource.LoadInitialParams(2, true)
    private val mockParams: PageKeyedDataSource.LoadParams<Int> =
        PageKeyedDataSource.LoadParams(2, 12)

    private val quakeCaptor = argumentCaptor<List<Earthquake>>()
    private val previousPageKeyCaptor = argumentCaptor<Int>()
    private val nextPageKeyCaptor = argumentCaptor<Int>()

    private lateinit var quakesDataSource: QuakesDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        quakesDataSource = QuakesDataSource(testScope, mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `initial paging load failed`() = testDispatcher.runBlockingTest {

        whenever(mockService.getQuakes(any(), any(), any(), any(), any(), any(), any(), any())).thenThrow(mockHttpException)

        quakesDataSource.loadInitial(mockInitialParams, mockInitialCallback)

        verify(mockInitialCallback, never()).onResult(emptyList(), null, 2)
    }

    @Test
    fun `initial paging load successful`() = testDispatcher.runBlockingTest {

        val quakes: Earthquakes = Earthquakes(earthquakes = listOf(quake, quake, quake))

        whenever(mockService.getQuakes(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(mockResponse)
        whenever(mockResponse.isSuccessful).thenReturn(true)
        whenever(mockResponse.body()).thenReturn(quakes)

        quakesDataSource.loadInitial(mockInitialParams, mockInitialCallback)

        verify(mockInitialCallback).onResult(quakeCaptor.capture(), previousPageKeyCaptor.capture(), nextPageKeyCaptor.capture())
        assertEquals(3, quakeCaptor.firstValue.size)
        assertNull(previousPageKeyCaptor.firstValue)
        assertEquals(2, nextPageKeyCaptor.firstValue)
    }

    @Test
    fun `load after paging load failed`() = testDispatcher.runBlockingTest {

        whenever(mockService.getQuakes(any(), any(), any(), any(), any(), any(), any(), any())).thenThrow(mockHttpException)

        quakesDataSource.loadAfter(mockParams, mockCallback)

        verify(mockCallback, never()).onResult(emptyList(), 3)
    }

    @Test
    fun `load after paging load successful`() = testDispatcher.runBlockingTest {

        whenever(mockService.getQuakes(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(mockResponse)
        whenever(mockResponse.isSuccessful).thenReturn(true)
        whenever(mockResponse.body()).thenReturn(Earthquakes())

        quakesDataSource.loadAfter(mockParams, mockCallback)

        verify(mockCallback).onResult(emptyList(), 3)
    }
}
