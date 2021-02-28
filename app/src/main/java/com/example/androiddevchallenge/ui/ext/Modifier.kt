package com.example.androiddevchallenge.ui.ext

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import kotlin.math.roundToInt

fun Modifier.percentOffsetX(percent: Float): Modifier =
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(
                (placeable.width.toFloat() * percent).roundToInt(),
                0
            )
        }
    }
