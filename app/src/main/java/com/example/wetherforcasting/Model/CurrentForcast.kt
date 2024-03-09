package com.example.wetherforcasting.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentForcast(
    var coord: Coord? = null,
    var weather: ArrayList<Weather>? = null,
    var base: String,
    var main: Main? = null,
    var visibility: Int? = null,
    var wind: Wind? = null,
    var clouds: Clouds? = null,
    var dt: Long? = null,
    var sys: Sys? = null,
    var timezone: Long? = null,
    var id: Long? = null,
    var name: String? = null,
    var cod: Int? = null
) : Parcelable

@Parcelize
data class Coord(
    var lon: Double? = null,
    var lat: Double? = null
) : Parcelable

@Parcelize
data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
) : Parcelable

@Parcelize
data class Main(
    var temp: Double? = null,
    var feels_like: Double? = null,
    var temp_min: Double? = null,
    var temp_max: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null
) : Parcelable

@Parcelize
data class Wind(
    var speed: Double? = null,
    var deg: Int? = null
) : Parcelable

@Parcelize
data class Clouds(
    var all: Int? = null
) : Parcelable

@Parcelize
data class Sys(
    var type: Int? = null,
    var id: Long? = null,
    var country: String? = null,
    var sunrise: Long? = null,
    var sunset: Long? = null
) : Parcelable
