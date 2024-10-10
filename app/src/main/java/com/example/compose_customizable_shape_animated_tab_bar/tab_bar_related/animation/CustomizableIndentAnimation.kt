package com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.animation

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.indent_shape.CustomizableIndentShape
import com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.indent_shape.IndentShapeData
import com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.toPxf

class CustomizableIndentAnimation(
    private val animationSpec: FiniteAnimationSpec<Float>,

    private var topFromCenterXOffset: Dp = 80.dp,
    private var curveDepth: Dp = 40.dp,
    private var curveApogeeHeight: Dp = 43.dp,
):IndentAnimation {
    @Composable
    override fun animateIndentShapeAsState(targetOffset: Offset): State<Shape> {

        if (targetOffset.isUnspecified) {
            val shape: Shape = CustomizableIndentShape(IndentShapeData())
            return remember { mutableStateOf( shape ) }
        }

        val density = LocalDensity.current

        val position = animateFloatAsState(
            targetValue = targetOffset.x,
            animationSpec = animationSpec, label = "Current Tab X Position Animation"
        )

        return produceState(
            initialValue = CustomizableIndentShape(
                indentShapeData = IndentShapeData(
                    topFromCenterXOffset = topFromCenterXOffset.toPxf(density),
                    curveDepth = curveDepth.toPxf(density),
                    curveApogeeHeight = curveApogeeHeight.toPxf(density)
                )
            ),
            key1 = position.value
        ) {
            this.value = value.copy(xIndentPosition = position.value,
                //By Applying ALL Values during the copy, the shape can CHANGE during animation
                topFromCenterXOffset = topFromCenterXOffset.toPxf(density),
                curveDepth = curveDepth.toPxf(density),
                curveApogeeHeight = curveApogeeHeight.toPxf(density)
            )
        }




    }
}