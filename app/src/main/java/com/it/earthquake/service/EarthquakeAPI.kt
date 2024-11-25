package com.it.earthquake.service

import com.it.earthquake.model.EarthquakeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeAPI {
    // Request Example: https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-01-01&endtime=2024-01-01&minmagnitude=6
    @GET("query")
    fun getEarthquakes(
        @Query("format") format: String? = "geojson",
        @Query("starttime") starttime: String? = "2023-01-01",
        @Query("endtime") endtime: String? = "2024-01-01",
        @Query("minmagnitude") minmagnitude: Int? = 6
    ): Single<EarthquakeResponse>
}