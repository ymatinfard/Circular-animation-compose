package com.matin.simcard.model

data class WifiLocation(
    val id: Int,
    val title: String,
    val lat: Double,
    val lng: Double,
    val isOnline: Boolean = false,
    val isOftenWork: Boolean = false,
)

val fakeWifiLocations = listOf(
    WifiLocation(
        id = 123,
        title = "123",
        lat = 38.208944757256624,
        lng = 15.557370297498132
    ),
    WifiLocation(
        id = 124,
        title = "124",
        lat = 38.19346658305723,
        lng = 15.553883918360398
    )
)
