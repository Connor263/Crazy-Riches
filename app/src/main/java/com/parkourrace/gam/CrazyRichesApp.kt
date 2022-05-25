package com.parkourrace.gam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.parkourrace.gam.ui.InitScreen
import com.parkourrace.gam.ui.game.GameScreen
import com.parkourrace.gam.ui.game.ScoreScreen
import com.parkourrace.gam.ui.game.menu.MenuScreen
import com.parkourrace.gam.ui.game.options.OptionScreen
import com.parkourrace.gam.ui.game.rules.RuleScreen
import com.parkourrace.gam.ui.web.TetLoadingElytsWebScreenWebScreen

@Composable
fun CrazyRichesApp(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colors.background
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "init"
        ) {
            composable("init") { InitScreen(viewModel) }

            composable("menu") { MenuScreen(navController) }

            composable("option") { OptionScreen(navController) }

            composable("rule") { RuleScreen(navController) }

            composable("game") { GameScreen(navController) }

            composable(
                "score/{score}", arguments = listOf(
                    navArgument("score") {
                        type = NavType.IntType
                        defaultValue = 0
                    })
            ) { backStackEntry ->
                ScoreScreen(
                    navController,
                    backStackEntry.arguments?.getInt("score") ?: 0
                )
            }

            composable("web/{url}", arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })) { backStackEntry ->
                backStackEntry.arguments?.getString("url")?.let { url ->
                    TetLoadingElytsWebScreenWebScreen(navController, url)
                }
            }
        }
    }

    LaunchedEffect(viewModel.routeString.value) {
        val route = viewModel.routeString.value
        if (route.isNotBlank()) {
            navController.navigate(route) {
                popUpTo("init") {
                    inclusive = true
                }
            }
        }
    }
}
