package com.matin.instabridge.data

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.matin.instabridge.DefaultTestDevices
import com.matin.instabridge.captureForDevice
import com.matin.instabridge.designsystem.component.InstabridgeProgressIndicator
import com.matin.instabridge.designsystem.theme.InstabridgeTheme
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import java.util.TimeZone

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class DataScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setTimeZone() {
        // Make time zone deterministic in tests
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Test
    fun dataScreenProgressBar() {
        composeTestRule.captureForDevice(
            deviceName = "phone_light",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "DataScreenProgressBar",
            darkMode = false
        )
        {
            InstabridgeTheme {
                InstabridgeProgressIndicator(
                    maxValue = 20f,
                    currentValue = "0",
                    progressBackgroundColor = MaterialTheme.colorScheme.surface,
                    progressIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                )
            }
        }
    }
}