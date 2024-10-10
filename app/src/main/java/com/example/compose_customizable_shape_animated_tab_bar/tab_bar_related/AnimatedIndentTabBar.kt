package com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.animation.CustomizableIndentAnimation
import com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.layouts.rowLayoutPolicy

@Composable
fun AnimatedIndentTabBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    barColor: Color = Color.Blue,
    underColor:Color = Color.Cyan,
    tabBarAnimationSpec: FiniteAnimationSpec<Float>,

    barButtonsContent: @Composable () -> Unit
    ){

    var itemPositions by remember { mutableStateOf(listOf<Offset>()) }


    val tabBarLayoutMeasurePolicy = rowLayoutPolicy {
        itemPositions = it.map { xCord ->
            Offset(xCord, 0f)
        }
    }

    val selectedItemOffset by remember(selectedIndex, itemPositions) {
        derivedStateOf {
            if (itemPositions.isNotEmpty()) itemPositions[selectedIndex] else Offset.Unspecified
        }
    }


    //DECLARE The INDENT SHAPE ANIMATION
    val indentShapeAnimation = CustomizableIndentAnimation(
        tabBarAnimationSpec,

        topFromCenterXOffset = getIndentTopFromCenterXOffset(selectedIndex),
        curveDepth = getCurveDepth(selectedIndex),

        curveApogeeHeight = getCurveApogeeHeight(selectedIndex)
    )

    //Declare animate State
    val middleIndentShapeState = indentShapeAnimation.animateIndentShapeAsState(
        targetOffset = selectedItemOffset
    )


    Row {
        Spacer(modifier = Modifier.weight(1f) )

        Box(modifier = modifier
            .offset(y = -50.dp)
        ){

            Box(modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(underColor)

            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    //clip the background and the bar SEPARATELY from the layout and items
                    .graphicsLayer {
                        clip = true
                        shape = middleIndentShapeState.value
                    }

                    .clip(RoundedCornerShape(15.dp))
                    .background(barColor)

            )




            Layout(
                modifier = Modifier
                    .fillMaxHeight()
                ,
                content = barButtonsContent,
                measurePolicy = tabBarLayoutMeasurePolicy
            )


        }

        Spacer(modifier = Modifier.weight(1f) )

    }



}


/**
 * Return a different value for the indent distance away from the center, depending on which index is selected
 * @param selectedItemIndex the selected tab index to test on.
 *
 * This allows for the indent to slightly change its shape depending on which tab is selected.
 */
fun getIndentTopFromCenterXOffset(selectedItemIndex: Int): Dp {
    return if (selectedItemIndex == 1) {
        //Middle Button Selected
        80.dp
    } else {
        //Any other width
        46.dp
    }
}


/**
 * Return a different value for the Curve Depth Depending on which index is selected
 * @param selectedItemIndex the selected tab index to test on.
 *
 * This allows for the indent to slightly change its curve depth depending on which tab is selected.
 */
fun getCurveDepth(selectedItemIndex: Int): Dp {
    return if (selectedItemIndex == 1) {
        //Middle Button Selected
        40.dp
    } else {
        //Any other width
        36.dp
    }
}

/**
 * Return a different value for the Apogee Height of the indent, Depending on which index is selected
 * @param selectedItemIndex the selected tab index to test on.
 *
 * This allows for the indent to slightly change its curve apogee depending on which tab is selected.
 */
fun getCurveApogeeHeight(selectedItemIndex: Int): Dp {
    return if (selectedItemIndex == 1) {
        //Middle Button Selected
        43.dp
    } else {
        //Any other width
        42.dp
    }
}

