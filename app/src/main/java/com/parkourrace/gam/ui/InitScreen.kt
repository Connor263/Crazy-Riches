package com.parkourrace.gam.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.parkourrace.gam.MainViewModel

@Composable
fun InitScreen() {
    val viewModel:MainViewModel = viewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        if (viewModel.crazyRichesLoading.value) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(110.dp),
                    color = Color.White
                )
            }
        }

    }
}