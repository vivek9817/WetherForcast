package com.example.wetherforcasting.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FutureForcast(
    var cod: String? = null,
    var message: Int? = null,
    var cnt: Int? = 0,
    var list: ArrayList<Lists>? = null,
    var city: City? = null
) : Parcelable

@Parcelize
data class Lists(
    var dt: Long? = null,
    var main: Main? = null,
    var weather: ArrayList<Weather>? = null,
    var clouds: Clouds? = null,
    var wind: Wind? = null,
    var visibility: Long? = null,
    var pop: Int? = null,
    var sys: Sys? = null,
    var dt_txt: String? = null
) : Parcelable

@Parcelize
data class City(
    var id: Long? = null,
    var name: String? = null,
    var coord: Cord? = null,
    var country: String? = null,
    var population: Long? = null,
    var timezone: Long? = null,
    var sunrise: Long? = null,
    var sunset: Long? = null
) : Parcelable

@Parcelize
data class Cord(
    var lat: Float? = null,
    var lon: Float? = null
) : Parcelable


