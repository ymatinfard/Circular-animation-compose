package com.matin.simcard.feature.wifi

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.matin.simcard.model.WifiLocation
import com.matin.simcard.model.fakeWifiLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor() : ViewModel() {
    private val _mapUiState = MutableStateFlow(MapScreenUiState())
    val mapUiState: StateFlow<MapScreenUiState> = _mapUiState

    fun setCurrentLocation(lat: Double, lng: Double) {
        _mapUiState.update { state ->
            state.copy(
                cameraPositionState = CameraPositionState(
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(lat, lng), MAP_ZOOM
                    )
                ),
                // Fake nearby wifi locations
                wifiLocations = listOf(
                    WifiLocation(
                        123,
                        "20 m",
                        lat + 0.0001,
                        lng,
                        isOnline = true,
                        isOftenWork = true
                    ),
                    WifiLocation(
                        124,
                        title = "92 m",
                        lat = lat,
                        lng = lng + 0.0095,
                        isOnline = true,
                        isOftenWork = true
                    ),
                    WifiLocation(
                        125,
                        title = "85 m",
                        lat = lat - 0.0075,
                        lng = lng - 0.0075,
                        isOnline = false,
                        isOftenWork = false,
                    ),
                )
            )
        }
    }

    companion object {
        const val MAP_ZOOM = 14F
    }
}

data class MapScreenUiState(
    val mapUiSettings: MapUiSettings = MapUiSettings(myLocationButtonEnabled = true),
    val mapProperties: MapProperties = MapProperties(isMyLocationEnabled = true),
    val wifiLocations: List<WifiLocation> = fakeWifiLocations,
    val cameraPositionState: CameraPositionState = CameraPositionState(),
)