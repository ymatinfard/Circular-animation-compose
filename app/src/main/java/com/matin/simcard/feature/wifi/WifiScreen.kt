package com.matin.simcard.feature.wifi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.matin.simcard.R
import com.matin.simcard.designsystem.component.WifiLocationItem
import com.matin.simcard.designsystem.theme.AdjustSystemBarColor
import com.matin.simcard.designsystem.util.DEFAULT_BOTTOM_NAV_HEIGHT
import com.matin.simcard.designsystem.util.bitmapDescriptorFromVector

import com.matin.simcard.model.WifiLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiScreen(viewModel: MapScreenViewModel) {
    AdjustSystemBarColor(
        statusBarColor = Color.Transparent,
        navigationBarColor = MaterialTheme.colorScheme.tertiary
    )

    val context = LocalContext.current
    val uiState by viewModel.mapUiState.collectAsStateWithLifecycle()
    var selectedWifiId by remember {
        mutableIntStateOf(NO_WIFI_SELECTED)
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    getCurrentLocation(context) { lat, lng ->
                        viewModel.setCurrentLocation(lat, lng)
                    }
                }
            })

    LaunchedEffect(Unit) {
        handleLocationPermission(context, viewModel, requestPermissionLauncher)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = DEFAULT_BOTTOM_NAV_HEIGHT)
    ) {
        GoogleMapContent(uiState) {
            selectedWifiId = it
        }

        WifiLocationsBottomSheet(
            state = rememberBottomSheetScaffoldState(),
            wifiLocations = uiState.wifiLocations
        ) {}
    }
}

@Composable
fun GoogleMapContent(uiState: MapScreenUiState, onMarkerClick: (Int) -> Unit) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = uiState.cameraPositionState,
        uiSettings = uiState.mapUiSettings,
        properties = uiState.mapProperties,
    ) {
        uiState.wifiLocations.forEach { location ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        location.lat,
                        location.lng
                    ),
                ),
                title = location.title,
                onClick = {
                    onMarkerClick(location.id)
                    false
                },
                icon = bitmapDescriptorFromVector(
                    context = LocalContext.current,
                    vectorResId = R.drawable.ic_map_marker,
                    text = location.title,
                    isOnline = location.isOnline
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiLocationsBottomSheet(
    state: BottomSheetScaffoldState,
    wifiLocations: List<WifiLocation>,
    wifiLocationClick: (WifiLocation) -> Unit = {}
) {
    BottomSheetScaffold(
        scaffoldState = state,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContainerColor = MaterialTheme.colorScheme.tertiary,
        sheetContent = { WifiLocationsBottomSheetContent(wifiLocations, wifiLocationClick) },
    ) {}
}

@Composable
private fun WifiLocationsBottomSheetContent(
    wifiLocations: List<WifiLocation>,
    wifiLocationClick: (WifiLocation) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn {
            items(wifiLocations) { wifiLocation ->
                WifiLocationItem(wifiLocation = wifiLocation)
            }
        }
    }
}

private fun handleLocationPermission(
    context: Context,
    viewModel: MapScreenViewModel,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {
    if (hasLocationPermission(context)) {
        getCurrentLocation(context) { lat, lng ->
            viewModel.setCurrentLocation(lat, lng)
        }
    } else {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val lat = location.latitude
            val long = location.longitude
            callback(lat, long)
        }
    }
        .addOnFailureListener { exception ->
            exception.printStackTrace()
        }
}

const val NO_WIFI_SELECTED = -1
