package com.matin.instabridge

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.DarkMode
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.RoborazziOptions.RecordOptions
import com.github.takahirom.roborazzi.captureRoboImage
import org.robolectric.RuntimeEnvironment

val DefaultRoborazziOptions =
    RoborazziOptions(
        // Pixel-perfect matching
        compareOptions = CompareOptions(changeThreshold = 0f),
        // Reduce the size of the PNGs
        recordOptions = RecordOptions(resizeScale = 0.5),
    )

enum class DefaultTestDevices(val description: String, val spec: String) {
    PHONE("phone", "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480"),
    FOLDABLE("foldable", "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"),
    TABLET("tablet", "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"),
}

data class TestDeviceSpecs(val width: Int, val height: Int, val dpi: Int)

private fun extractSpecs(deviceSpec: String): TestDeviceSpecs {
    val specs = deviceSpec.substringAfter("spec:")
        .split(",").map { it.split("=") }.associate { it[0] to it[1] }
    val width = specs["width"]?.toInt() ?: 640
    val height = specs["height"]?.toInt() ?: 480
    val dpi = specs["dpi"]?.toInt() ?: 480
    return TestDeviceSpecs(width, height, dpi)
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureForDevice(
    deviceName: String,
    deviceSpec: String,
    screenshotName: String,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    darkMode: Boolean = false,
    body: @Composable () -> Unit,
) {
    val (width, height, dpi) = extractSpecs(deviceSpec)

    // Set qualifiers from specs
    RuntimeEnvironment.setQualifiers("w${width}dp-h${height}dp-${dpi}dpi")

    this.activity.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            DeviceConfigurationOverride(
                override = DeviceConfigurationOverride.Companion.DarkMode(darkMode),
            ) {
                body()
            }
        }
    }
    this.onRoot()
        .captureRoboImage(
            "src/test/screenshots/${screenshotName}_$deviceName.png",
            roborazziOptions = roborazziOptions,
        )
}