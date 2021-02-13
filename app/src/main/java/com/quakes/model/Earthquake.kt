package com.quakes.model

data class Earthquake(
    val datetime: String?,
    val depth: Number?,
    val lng: Number?,
    val src: String?,
    val eqid: String?,
    val magnitude: Number?,
    val lat: Number?
)
