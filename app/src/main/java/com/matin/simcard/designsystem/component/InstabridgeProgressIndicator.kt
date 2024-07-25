package com.matin.simcard.designsystem.component


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matin.simcard.R

@Composable
fun simCardProgressIndicator(
    currentValue: String,
    maxValue: Float,
    progressBackgroundColor: Color,
    progressIndicatorColor: Color,
    modifier: Modifier = Modifier,
) {

    val stroke = with(LocalDensity.current) {
        Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    val configuration = LocalConfiguration.current
    val progressWidth = configuration.screenWidthDp * .6

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ProgressValueLabel(
            currentValue = currentValue
        )

        val animateFloat = remember { Animatable(0f) }
        LaunchedEffect(currentValue) {
            animateFloat.animateTo(
                targetValue = currentValue.toFloat() / maxValue,
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
            )
        }

        Canvas(
            Modifier
                .progressSemantics(currentValue.toFloat() / maxValue)
                .size(progressWidth.dp)
        ) {
            // Start at 6 O'clock
            val startAngle = 90f
            val sweep: Float = animateFloat.value * 360f
            val diameterOffset = stroke.width / 2

            drawCircle(
                color = progressBackgroundColor,
                style = stroke,
                radius = size.minDimension / 2.0f - diameterOffset
            )
            drawCircularProgressIndicator(
                startAngle,
                sweep,
                progressIndicatorColor,
                stroke,
                progressBackgroundColor
            )
        }
    }
}

private fun DrawScope.drawCircularProgressIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
    backgroundColor: Color
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )

    // Calculate the position of the end point of the arc
    val endAngle = (startAngle + sweep) % 360
    val radius = size.minDimension / 2 - diameterOffset
    val center = Offset(size.width / 2, size.height / 2)

    val endPoint = Offset(
        x = center.x + radius * kotlin.math.cos(Math.toRadians(endAngle.toDouble())).toFloat(),
        y = center.y + radius * kotlin.math.sin(Math.toRadians(endAngle.toDouble())).toFloat()
    )

    // Draw the filled circle at the end point of the arc
    drawCircle(
        color = color,
        radius = stroke.width + 10,
        center = endPoint
    )

    drawCircle(
        color = backgroundColor,
        radius = stroke.width / 2,
        center = endPoint
    )
}

@Composable
private fun ProgressValueLabel(
    currentValue: String,
) {
    val currentValueFloat = currentValue.toFloatOrNull() ?: 0f

    val animatedValue by animateFloatAsState(
        targetValue = currentValueFloat,
        animationSpec = tween(2000), label = ""
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (animatedValue % 1 == 0f) {
                animatedValue.toInt().toString()
            } else {
                String.format("%.1f", animatedValue)
            },
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = stringResource(R.string.gb),
            style = MaterialTheme.typography.displaySmall
        )
    }
}


