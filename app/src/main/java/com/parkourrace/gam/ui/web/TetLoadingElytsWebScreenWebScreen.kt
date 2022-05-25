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
import com.parkourrace.gam.ui.game.navigateToGame

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TetLoadingElytsWebScreenWebScreen(navController: NavHostController, url: String) {
    Log.d("TAG", "CrazyWebScreen: $url")
    val detLinkLoadNavigator = rememberWebViewNavigator()
    val etLinkLoadncrazyState = rememberWebViewState(url = url)

    val oadingElytsWedingElytsWebScreenFileData by remember { mutableStateOf<ValueCallback<Uri>?>(null) }
    var tLinkLoaeenFilePath by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }

    fun elytsWebProcessResult(result: Intent?) {
        if (oadingElytsWedingElytsWebScreenFileData == null && tLinkLoaeenFilePath == null) return

        var abslelytsRoukrapResultData: Uri? = null
        var abslelytsRoukrapResultFilePath: Array<Uri>? = null
        result?.let { data ->
            abslelytsRoukrapResultData = data.data
            abslelytsRoukrapResultFilePath = arrayOf(Uri.parse(data.dataString))
        }
        oadingElytsWedingElytsWebScreenFileData?.onReceiveValue(abslelytsRoukrapResultData)
        tLinkLoaeenFilePath?.onReceiveValue(abslelytsRoukrapResultFilePath)
    }

    val abslelytsRoukrapStyleStartForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) elytsWebProcessResult(result.data)
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        WebView(
            state = etLinkLoadncrazyState,
            navigator = detLinkLoadNavigator,
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
                    tLinkLoaeenFilePath = filePathCallback
                    Intent(Intent.ACTION_GET_CONTENT).run {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                        abslelytsRoukrapStyleStartForResult.launch(this)
                    }
                    return true
                }
            }
        )
    }

    BackHandler {
        etLinkLoadncrazyState.loadingState.let { state ->
            if (state is LoadingState.Loading ||
                state is LoadingState.Finished && detLinkLoadNavigator.canGoBack
            ) {
                detLinkLoadNavigator.navigateBack()
            }
        }
    }
}