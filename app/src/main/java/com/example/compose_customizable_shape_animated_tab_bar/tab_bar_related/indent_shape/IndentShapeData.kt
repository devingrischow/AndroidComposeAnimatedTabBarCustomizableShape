package com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.indent_shape



/**
 * The Indent Data For the Indent Shape.
 *
 * @param xIndentPosition The X-Position of the curve.
 * @param topFromCenterXOffset The X-Offset amount that spaces each side of the curve from the center position.
 * @param curveDepth The Depth of the curve.
 * @param curveApogeeHeight The Height of the Apogee of the curve.
 */
data class IndentShapeData(
    /**
    The X-Position of the curve.
     */
    val xIndentPosition: Float = 0f,

    val topFromCenterXOffset: Float = 0f,

    var curveDepth: Float = 0f,

    var curveApogeeHeight:Float = 0f,
)
