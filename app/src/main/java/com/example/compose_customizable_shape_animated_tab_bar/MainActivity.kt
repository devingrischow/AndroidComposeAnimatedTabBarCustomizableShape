package com.example.compose_customizable_shape_animated_tab_bar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_customizable_shape_animated_tab_bar.tab_bar_related.AnimatedIndentTabBar
import com.example.compose_customizable_shape_animated_tab_bar.ui.theme.Compose_Customizable_Shape_Animated_Tab_BarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleScreen()
        }
    }
}

@Composable
fun ExampleScreen() {

    var selectedItemIndex = remember {
        mutableStateOf(2)
    }

    val geoConfig = LocalConfiguration.current

    //The list of tabs the bar will have
    val tabs = listOf(
        BottomTabBarScreen.Timer,
        BottomTabBarScreen.Home,
        BottomTabBarScreen.Settings
    )


    //Declared chosen Bar background color
    val chosenBarColor = Color.Blue

    val underColor = Color.Cyan

    val chosenAnimationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = 300)

    Scaffold(
        bottomBar = {

            AnimatedIndentTabBar(
                modifier = Modifier

                    .width((geoConfig.screenWidthDp / 1.1).dp)

                    .height(61.dp)
                ,

                //--------------
                tabBarAnimationSpec = chosenAnimationSpec,

                selectedIndex = selectedItemIndex.value,

                barColor = chosenBarColor,
                underColor = underColor

            ){


                //for every tab in the tab list, and will make buttons for each of the tab button
                tabs.forEachIndexed { index, tab ->

                    val indentHeightAnimation = animateDpAsState(targetValue = getYButtonHeight(buttonIndex = index, selectedItemIndex),
                        label = "Indent Icon Height Animation"
                    )

                    val bubbleScaleEffect = animateFloatAsState(targetValue = getBackgroundScaleForIcons(buttonIndex = index, selectedItemIndex),
                        animationSpec = tween(durationMillis = 450),
                        label = "Scaling Bubble Effect"
                    )


                    Button(
                        shape = CircleShape,
                        colors = ButtonColors(
                            contentColor = Color.White,
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        ),
                        onClick = {
                            Log.d("Pressed Button", "Pressed On Tab Button ${tab.route}")

                            selectedItemIndex.value = index

                        },
                        modifier = Modifier
                            .offset(y = indentHeightAnimation.value)

                            //OUTER MOST SCALE
                            //This is the largest part of the object, AKA the part the animation GROWS into
                            .graphicsLayer {
                                scaleY = if (index == 1) 1.6f else 1.3f
                                scaleX = if (index == 1) 1.6f else 1.3f
                            }
                    ) {


                        //Outer Circle Holder
                        //(this contains the light blue secondary container background to grow
                        Box(contentAlignment = Alignment.Center){


                            Box(
                                modifier = Modifier


                                    .size(30.dp)

                                    .scale(bubbleScaleEffect.value)


                                    .clip(CircleShape)


                                    .background(underColor)


                            )

                            Box(
                                modifier = Modifier

                                    .size(30.dp)
                                    //Size of the Icon Holder Bubble
                                    .scale(if (index == 1) 1.6f else 1.4f)

                                    .clip(CircleShape)

                                    .background(chosenBarColor)


                            )



                            //Icon Container/Item
                            Icon(
                                imageVector = ImageVector.vectorResource(id = tab.icon),
                                contentDescription = "${tab.route} Tab Button",
                                modifier = Modifier
                                    .scale(1f)



                            )

                        }












                        //Bottom Of Button
                    }

                }


            }//Bottom of Tab Bar Content

        }//Bottom of Bottom Bar Declaration
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //Column Body

            Text(text = "Selected Tab ${selectedItemIndex.value}",
                color = Color.Black
            )

        }

    }//Bottom Of Scaffold

}


/**
 * Returns a different height for the button icons depending on both the BUTTONS INDEX and the SELECTED INDEX
 * @param buttonIndex The index of the button
 * @param selectedItemIndex The index of the current selected item
 */
fun getYButtonHeight(buttonIndex: Int, selectedItemIndex: MutableState<Int>): Dp {
    return if (buttonIndex == selectedItemIndex.value && buttonIndex == 1) {
        //Middle Button Selected
        -36.dp
    } else if (buttonIndex == selectedItemIndex.value && buttonIndex != 1) {
        -29.dp
    }else if (buttonIndex == 1) {
        -20.dp
    } else {
        0.dp
    }
}


/**
 * Returns a different scale for the buttons background animation to use depending on the BUTTONS INDEX and the SELECTED INDEX
 * @param buttonIndex The index of the button
 * @param selectedItemIndex The index of the current selected item
 */
fun getBackgroundScaleForIcons(buttonIndex: Int, selectedItemIndex: MutableState<Int>): Float {
    return if (buttonIndex == selectedItemIndex.value && buttonIndex == 1) {
        //Middle Button Selected
        1.9f
    } else if (buttonIndex == selectedItemIndex.value && buttonIndex != 1) {
        1.8f
    }else{
        0f
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExampleScreen()
}


sealed class BottomTabBarScreen(
    val route: String,
    val icon: Int
) {
    object Timer : BottomTabBarScreen(
        route = "timer",
        icon = R.drawable.clock
    )

    object Home : BottomTabBarScreen(
        route = "home",
        icon = R.drawable.home
    )

    object Settings : BottomTabBarScreen(
        route = "settings",
        icon = R.drawable.settings
    )
}