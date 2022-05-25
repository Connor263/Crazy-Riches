package com.parkourrace.gam.ui.game.options

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.parkourrace.gam.R
import com.parkourrace.gam.ui.game.composables.RuleView
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@Composable
fun OptionScreen(navController: NavController) {
    val viewModel: OptionsViewModel = viewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var entered by remember { mutableStateOf(false) }
    var indexAnim by remember { mutableStateOf(0) }
    val fallingAnimation = remember { Animatable(-800F) }
    val alphaAnimation = remember { Animatable(1F) }

    val radioSelected = viewModel.getOption(context).collectAsState(initial = 1)

    Image(
        painter = painterResource(id = R.drawable.background_3),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Level", modifier = Modifier
                    .absoluteOffset(y = fallingAnimation.value.dp)
                    .alpha(alphaAnimation.value),
                fontSize = 48.sp,
                color = Color.White
            )
            repeat(4) {
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier
                            .absoluteOffset(y = fallingAnimation.value.dp)
                            .alpha(alphaAnimation.value),
                        onClick = { viewModel.saveOption(context, it + 1) },
                        selected = radioSelected.value == it + 1
                    )
                    Text(
                        (it + 1).toString(),
                        modifier = Modifier
                            .absoluteOffset(y = fallingAnimation.value.dp)
                            .alpha(alphaAnimation.value),
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }
        }
    }

    LaunchedEffect(entered) {
        indexAnim++
    }

    LaunchedEffect(indexAnim) {
        fallingAnimation.animateTo(
            targetValue = fallingAnimation.value + 50F,
            animationSpec = tween(durationMillis = 50)
        ).apply {
            when (this.endReason) {
                AnimationEndReason.BoundReached -> {}
                AnimationEndReason.Finished -> {
                    Log.d("TAG", "RuleScreen: ")
                    if (fallingAnimation.value + 50F <= 0) {
                        indexAnim++
                    }
                }
            }
        }
        entered = true
    }


    BackHandler {
        scope.launch {
            alphaAnimation.animateTo(
                0F, animationSpec = tween(400)
            ).apply {
                when (endReason) {
                    AnimationEndReason.BoundReached -> {}
                    AnimationEndReason.Finished -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}