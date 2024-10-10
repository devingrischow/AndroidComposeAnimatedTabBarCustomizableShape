package com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape

interface IndentAnimation {

    @Composable
    fun animateIndentShapeAsState(
        targetOffset: Offset
    ): State<Shape>

}