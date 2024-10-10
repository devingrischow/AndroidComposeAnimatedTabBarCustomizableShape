package com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.indent_shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


/**
 * The Custom SHAPE object for Indents to be based off of.
 */
class CustomizableIndentShape(
    private val indentShapeData: IndentShapeData
): Shape {


    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ):Outline {
        return Outline.Generic(
            Path().createIndentShape(size, layoutDirection, indentShapeData)
        )

    }


    fun copy(
        xIndentPosition: Float = indentShapeData.xIndentPosition,

        topFromCenterXOffset: Float = indentShapeData.topFromCenterXOffset,

        curveDepth: Float = indentShapeData.curveDepth,

        curveApogeeHeight:Float = indentShapeData.curveApogeeHeight,
    ) = CustomizableIndentShape(
        indentShapeData.copy(
            xIndentPosition = xIndentPosition,
            topFromCenterXOffset = topFromCenterXOffset,
            curveDepth = curveDepth,
            curveApogeeHeight = curveApogeeHeight
        )
    )


}


/**
 * The Path function for the custom indent shape on the tab bar.
 *
 * Takes the following parameters.
 * @param size The Size of the Tab Bar
 * @param layoutDirection The Layout Direction of the Tab Bar
 * @param indentShapeData The Shape Data for the Indent Shape
 */
fun Path.createIndentShape(
    size:Size,
    layoutDirection: LayoutDirection,
    indentShapeData: IndentShapeData

): Path {

    return apply {


        //The Height and Width of the Tab Bar
        val barAreaWidth = size.width
        val barAreaHeight = size.height

        //Shape Datas XIndentPosition is the position of the curve

        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close() // Close the initial rectangle path

        //Move to xposition at the top of the bar
        val indentXPosition = indentShapeData.xIndentPosition

        //Move to the top left of the bar
        //take the center - the top curve offset
        moveTo(indentXPosition - indentShapeData.topFromCenterXOffset, 0f)


        val to1 = Offset(indentXPosition, indentShapeData.curveApogeeHeight)
        val control1 = Offset(indentXPosition - indentShapeData.curveDepth, 0f)
        val control2 =
            Offset(indentXPosition - indentShapeData.curveDepth, indentShapeData.curveApogeeHeight)

        val to2 = Offset(indentXPosition + indentShapeData.topFromCenterXOffset, 0f)
        val control3 =
            Offset(indentXPosition + indentShapeData.curveDepth, indentShapeData.curveApogeeHeight)
        val control4 = Offset(indentXPosition + indentShapeData.curveDepth, 0f)


        cubicTo(

            //Control Points 1
            control1.x,
            control1.y,

            //Control Point 2
            control2.x,
            control2.y,

            //Curve To
            to1.x,
            to1.y,
        )

        cubicTo(
            //Control Points 1
            control3.x,
            control3.y,

            //Control Point 2
            control4.x,
            control4.y,

            //Curve To
            to2.x,
            to2.y,
        )


    }

}