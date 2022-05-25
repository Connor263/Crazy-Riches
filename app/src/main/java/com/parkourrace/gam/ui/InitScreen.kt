package com.parkourrace.gam.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkourrace.gam.MainActivity
import com.parkourrace.gam.MainViewModel

@Composable
fun InitScreen(viewModel: MainViewModel = viewModel()) {
    val mainActivity = LocalContext.current as MainActivity
    var loading by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        if (loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(110.dp),
                    color = Color.White
                )
            }
        } else {
            Log.d("TAG", "InitScreen: 22")
            AlertDialog(
                title = {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No internet connection", fontSize = 24.sp)
                },
                text = {
                    Text(
                        "Check your internet connection and try again later",
                        fontSize = 16.sp
                    )
                },
                onDismissRequest = { viewModel.tetParkourStylesLoading.value = true },
                confirmButton = {
                    TextButton(onClick = { mainActivity.tetParkourLinkElytsLoadsading() }) {
                        Text(text = "Try again", fontSize = 16.sp)
                    }
                })
        }
    }

    LaunchedEffect(viewModel.tetParkourStylesLoading.value) {
        loading = viewModel.tetParkourStylesLoading.value
    }
}