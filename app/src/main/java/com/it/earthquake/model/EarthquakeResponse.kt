package com.it.earthquake.model

import com.google.gson.annotations.SerializedName

data class EarthquakeResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("matadata")
    val metadata: Metadata,
    @SerializedName("features")
    val features: ArrayList<Feature>,
    @SerializedName("bbox")
    val bbox: List<Double>
)