package com.matin.simcard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.matin.simcard.designsystem.component.simCardBottomBar
import com.matin.simcard.navigation.simCardNavHost
import com.matin.simcard.navigation.TopLevelDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SimCardApp() {
    val navController = rememberNavController()
    var selectedDestination by remember {
        mutableStateOf(TopLevelDestination.DATA)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            simCardBottomBar(
                currentDestination = selectedDestination,
                bottomNavItems = TopLevelDestination.entries,
                onNavigationToDestination = {
                    navController.navigate(it.route)
                    selectedDestination = it
                })
        }) {
        Surface {
            simCardNavHost(navController)
        }
    }
}