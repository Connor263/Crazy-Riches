@file:Suppress("DEPRECATION")

package com.parkourrace.gam.ui.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.google.accompanist.web.*
import com.parkourrace.gam.ui.game.menu.navigateToGame

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CrazyWebScreen(navController: NavHostController, url: String) {
    Log.d("TAG", "CrazyWebScreen: $url")
    val crazyNavigator = rememberWebViewNavigator()
    val crazyState = rememberWebViewState(url = url)

    val crazyFileData by remember { mutableStateOf<ValueCallback<Uri>?>(null) }
    var crazyFilePath by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    fun crazyProcessResult(result: Intent?) {
        if (crazyFileData == null && crazyFilePath == null) return

        var crazyResultData: Uri? = null
        var crazyResultFilePath: Array<Uri>? = null
        result?.let { data ->
            crazyResultData = data.data
            crazyResultFilePath = arrayOf(Uri.parse(data.dataString))
        }
        crazyFileData?.onReceiveValue(crazyResultData)
        crazyFilePath?.onReceiveValue(crazyResultFilePath)
    }

    val crazyStartForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) crazyProcessResult(result.data)
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        WebView(
            state = crazyState,
            navigator = crazyNavigator,
            captureBackPresses = false,
            onCreated = { webView ->
                webView.settings.apply {
                    javaScriptEnabled = true
                    allowContentAccess = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                    setSupportMultipleWindows(false)
                    builtInZoomControls = true
                    useWideViewPort = true
                    setAppCacheEnabled(true)
                    displayZoomControls = false
                    allowFileAccess = true
                    lightTouchEnabled = true
                }

                webView.clearCache(false)
                CookieManager.getInstance().setAcceptCookie(true)
                CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
            },
            client = object : AccompanistWebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    url?.let {
                        if (url.contains("error=appafAs3s") || url.contains("disabled.html")) {
                            navigateToGame(navController)
                        }
                    }
                }
            },
            chromeClient = object : AccompanistWebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    crazyFilePath = filePathCallback
                    Intent(Intent.ACTION_GET_CONTENT).run {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                        crazyStartForResult.launch(this)
                    }
                    return true
                }
            }
        )
    }

    BackHandler {
        crazyState.loadingState.let { state ->
            if (state is LoadingState.Loading ||
                state is LoadingState.Finished && crazyNavigator.canGoBack
            ) {
                crazyNavigator.navigateBack()
            }
        }
    }
}