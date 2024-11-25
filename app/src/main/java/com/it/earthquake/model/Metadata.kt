package com.it.earthquake.model

import com.google.gson.annotations.SerializedName

/**
 * Metadata
 *  元数据.
 *  Json Sample:
 *  <code>
 *      {
 *         "generated": 1732410473000,
 *         "url": "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-01-01&endtime=2024-01-01&minmagnitude=7",
 *         "title": "USGS Earthquakes",
 *         "status": 200,
 *         "api": "1.14.1",
 *         "count": 19
 *     },
 *  </code>
 *
 * @property generated Data Timestamp ?
 * @property url Request URL
 * @property title Body Title
 * @property status Request Status Code
 * @property api  API Version
 * @property count Data Count
 * @constructor Create empty Metadata
 */
data class Metadata(
    @SerializedName("generated")
    val generated: Long,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("api")
    val api: String,
    @SerializedName("count")
    val count: Int
)
