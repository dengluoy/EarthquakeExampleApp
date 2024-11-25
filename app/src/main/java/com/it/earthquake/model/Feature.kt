package com.it.earthquake.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Feature(
    @SerializedName("type")
    val type: String,
    @SerializedName("properties")
    val properties: Properties,
    @SerializedName("geometry")
    val geometry: Geometry,
    @SerializedName("id")
    val id: String
) : Parcelable {

}
