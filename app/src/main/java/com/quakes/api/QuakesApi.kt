package com.quakes.api

import com.quakes.model.Earthquakes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuakesApi {

    @GET("earthquakesJSON")
    suspend fun getQuakes(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("formatted") formatted: Boolean? = true,
        @Query("north") north: Double? = 44.1,
        @Query("south") south: Double? = -9.9,
        @Query("east") east: Double? = -22.4,
        @Query("west") west: Double? = 55.2,
        @Query("username") username: String? = "mkoppelman"
    ): Response<Earthquakes>
}