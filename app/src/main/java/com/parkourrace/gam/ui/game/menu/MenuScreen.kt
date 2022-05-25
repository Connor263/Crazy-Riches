package com.parkourrace.gam.ui.game.menu

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.parkourrace.gam.R

@Composable
fun MenuScreen(navController: NavController) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = R.drawable.background_3),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuTextButton(text = "Play") {
                navigateToGame(navController)
            }
            Spacer(Modifier.height(32.dp))
            MenuTextButton(text = "Exit") {
                (context as Activity).finish()
            }
        }
    }
}

@Composable
fun MenuTextButton(text: String, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.size(236.dp, 64.dp),
        onClick = onClick,
        elevation = ButtonDefaults.elevation(defaultElevation = 32.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.primary)
    ) {
        Text(text, fontSize = 28.sp, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

fun navigateToGame(navController: NavController) {
    navController.navigate("game")
}


@Preview(showBackground = true)
@Composable
fun MenuScreenPreview(navController: NavController = rememberNavController()) {
    MenuScreen(navController)
}