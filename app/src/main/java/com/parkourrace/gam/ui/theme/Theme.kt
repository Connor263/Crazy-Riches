package com.parkourrace.gam.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.ViewCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkourrace.gam.MainViewModel
import com.parkourrace.gam.data.game.preferences.GamePreferencesDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CrazyRichesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val font =
        GamePreferencesDataStore(LocalContext.current).font.collectAsState(initial = 0)

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography.copy(
            body1 = TextStyle(
                fontFamily = when (font.value) {
                    0 -> FontFamily.Default
                    1 -> FontFamily.Cursive
                    2 -> FontFamily.Serif
                    3 -> FontFamily.Monospace
                    else -> FontFamily.Default
                }
            )
        ),
        shapes = Shapes,
        content = content
    )
}