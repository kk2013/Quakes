package com.quakes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Earthquake(
    val datetime: String,
    val depth: Double?,
    val lng: Double,
    val src: String?,
    val eqid: String,
    val magnitude: Double,
    val lat: Double
) : Parcelable