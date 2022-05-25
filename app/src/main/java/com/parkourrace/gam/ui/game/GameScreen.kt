package com.parkourrace.gam.ui.game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.parkourrace.gam.R
import com.parkourrace.gam.ui.game.composables.Board
import com.parkourrace.gam.ui.game.composables.FocusBlockView
import com.parkourrace.gam.utils.TETRIS_COLUMN_SIZE
import com.parkourrace.gam.utils.TETRIS_ROW_SIZE

@Preview(showBackground = true)
@Composable
fun GameScreenPreview(navController: NavController = rememberNavController()) {
    GameScreen(navController)
}

@Composable
fun GameScreen(navController: NavController) {
    val viewModel: GameViewModel = viewModel()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val HEIGHT_STEP = remember { (screenHeight.value / TETRIS_ROW_SIZE).toInt() }
    val WIDTH_STEP = remember { (screenWidth.value / TETRIS_COLUMN_SIZE).toInt() }


    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_1),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Board()

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.25F)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    }, onClick = { viewModel.moveLeftSide() }
            ) {
                Image(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    alpha = 0.3F,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            IconButton(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.50F)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                    },
                onClick = { viewModel.enableQuickMoveDown() }
            ) {}

            IconButton(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.25F)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {

                    },
                onClick = { viewModel.moveRightSide() }
            ) {
                Image(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Filled.ArrowForward,
                    alpha = 0.3F,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = Color.DarkGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                "Score: ${viewModel.score.value}",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 36.sp
            )
            Spacer(Modifier.height(16.dp))
            FocusBlockView(
                type = viewModel.nextFocusBlock.value.type,
                widthStep = WIDTH_STEP / 2,
                heightStep = HEIGHT_STEP / 2
            )
            Spacer(Modifier.height(16.dp))
        }
    }


    LaunchedEffect(Unit) {
        viewModel.setLevel(context)
        viewModel.focusBlockIsCreated.value = true
    }

    LaunchedEffect(viewModel.gameOver.value) {
        if (viewModel.gameOver.value) {
            navController.navigate("score/${viewModel.score.value}") {
                popUpTo("game") { inclusive = true }
            }
        }
    }
}