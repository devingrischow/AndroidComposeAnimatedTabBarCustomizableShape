package com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable


//Based off designs from Exyte, https://github.com/exyte/AndroidAnimatedNavigationBar


@Composable
fun rowLayoutPolicy(

    onButtonPositionsCalculated:(ArrayList<Float>) -> Unit

) = remember {
    //Remember the tab bar policy from the array of the buttons positions
    tabBarMeasurePolicy( onButtonPositionsCalculated = onButtonPositionsCalculated)
}


internal fun tabBarMeasurePolicy(onButtonPositionsCalculated:(ArrayList<Float>) -> Unit) =
    MeasurePolicy { measurables, constraints ->
        //preform a internal check[check is a fuction]
        check(measurables.isNotEmpty()) {
            "There Must be at least ONE element to measure"
        }

        //Declare the item width (width of the item to fit
        val itemWidth = constraints.maxWidth / measurables.size

        //Declare the placeables
        val placeables = measurables.map { measurable ->
            //for each measurable, measure its size and constraints to calculate size values
            measurable.measure(constraints.copy(maxWidth = itemWidth))
        }

        //Use the max Width of the constraints for the width calculation
        val gap = calculateButtonGap(placeables, width = constraints.maxWidth)

        //Calculate the Height of the item
        val height = placeables.maxOf { it.height }

        //Assign the layout portion of the policy
        layout(width = constraints.maxWidth, height = height) {
            //set the xPosition to the gap
            var xPosition = gap

            //An Array for all the Icon positions in the layout
            val positions = ArrayList<Float>()

            //now for each of the placeables, calculate the relative positions
            placeables.forEachIndexed { index, placeable ->
                placeables[index].placeRelative(xPosition, 0)

                positions.add(
                    element = calculatePointPosition(xPosition, placeables[index].width)
                )

                //next take the xPosition and add the width of the placeables and the gap to it
                xPosition += placeables[index].width + gap
            }
            onButtonPositionsCalculated(positions)
        }//Bottom of layout

    }


//Calculate the Point Position of the bar and items
fun calculatePointPosition(xButtonPosition:Int, buttonWidth:Int):Float {
    //return the point position by taking the xButtonPosition and adding the buttonWidth / 2
    return xButtonPosition + (buttonWidth / 2f)
}


fun calculateButtonGap(placeables:List<Placeable>, width:Int):Int {
    //A running total for All of the
    var allWidth = 0

    //for each placeable in the list of placeables, add their widths to the running allWidth Total
    placeables.forEach { placeable ->
        allWidth += placeable.width
    }

    //to calculate the gap, take the width and subtract allWidth from it,
    //THEN,
    //Divice that by the size of the placeables array + 1
    val calculatedGap = (width - allWidth) / (placeables.size + 1)
    return calculatedGap
}




