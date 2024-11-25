package com.it.earthquake.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Properties(
    @SerializedName("place")
    val place: String,
    @SerializedName("mag")
    val mag: Float,
    @SerializedName("time")
    val time: Long,
    @SerializedName("updated")
    val updated: Long,
//    @SerializedName("tz")
//    val tz: Any?,
    @SerializedName("url")
    val url: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("felt")
    val felt: Int?,
    @SerializedName("cdi")
    val cdi: Float?,
    @SerializedName("mmi")
    val mmi: Double?,
    @SerializedName("alert")
    val alert: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tsunami")
    val tsunami: Int?,
    @SerializedName("sig")
    val sig: Int?,
    @SerializedName("net")
    val net: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("ids")
    val ids: String,
    @SerializedName("sources")
    val sources: String?,
    @SerializedName("types")
    val types: String,
    @SerializedName("nst")
    val nst: Int,
    @SerializedName("dmin")
    val dmin: Double,
    @SerializedName("rms")
    val rms: Double,
    @SerializedName("gap")
    val gap: Double,
    @SerializedName("magType")
    val magType: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("title")
    val title: String
) : Parcelable
