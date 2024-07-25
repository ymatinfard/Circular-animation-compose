package com.matin.simcard.designsystem.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.matin.simcard.model.DataPackage
import com.matin.simcard.model.DataUsage

val DEFAULT_BOTTOM_NAV_HEIGHT = 80.dp

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
    text: String,
    textColor: Int = Color.Black.hashCode(),
    circleRadius: Float = 10f,
    circleOffset: Float = 10f,
    isOnline: Boolean
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

    val circleColor = if (isOnline) Color.Green.hashCode() else Color.Red.hashCode()

    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bm)
    drawable.draw(canvas)

    val paint = Paint().apply {
        color = textColor
        textSize = 40f
        isFakeBoldText = true
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        style = Paint.Style.FILL
    }

    val xPos = canvas.width / 2
    val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 2) - 10f

    canvas.drawText(text, xPos.toFloat(), yPos, paint)

    val circlePaint = Paint().apply {
        color = circleColor
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    val circleX = xPos - circleOffset - 60f
    val circleY = yPos + (paint.descent() + paint.ascent()) / 2
    canvas.drawCircle(circleX, circleY, circleRadius, circlePaint)

    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun String.extractNumber(): String {
    val regex = """\d+(\.\d+)?""".toRegex()
    val matchResult = regex.find(this)
    val number = matchResult?.value?.toFloat() ?: 0f
    return if (number == number.toInt().toFloat()) {
        number.toInt().toString()
    } else {
        number.toString()
    }
}

val dataPackages = listOf(
    DataPackage("5.2 GB", true),
    DataPackage("10 GB", false),
    DataPackage("15 GB", false),
    DataPackage("20 GB", false),
)

val dataUsageItems = listOf(
    DataUsage(time = "30 hrs", type = "Internet"),
    DataUsage(time = "15 hrs", type = "Music"),
    DataUsage(time = "5 hrs", type = "Video"),
)