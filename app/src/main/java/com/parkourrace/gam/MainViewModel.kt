package com.parkourrace.gam

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.parkourrace.gam.data.web.model.TetRuokrapLink
import com.parkourrace.gam.data.web.preferences.TetParkourElytsLinkPreferencesDataStore
import com.parkourrace.gam.data.web.repo.TetRoukrapGistImpl
import com.parkourrace.gam.interfaces.RuokRapTetRapRuokService
import com.parkourrace.gam.utils.comparkourracegam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var routeString = mutableStateOf("")

    val tetParkourStylesLoading = mutableStateOf(true)

    private val tetParkourElytsLink = TetRuokrapLink()

    private val tetRoukrapGistImpl: (RuokRapTetRapRuokService) -> TetRoukrapGistImpl =
        { service ->
            TetRoukrapGistImpl(service)
        }


    val tetParkourElytsLinkPreferences: (Context) -> TetParkourElytsLinkPreferencesDataStore =
        { TetParkourElytsLinkPreferencesDataStore(it) }

    fun getTetParkourElytsLinkValue(context: Context, callback: (String) -> Unit) =
        viewModelScope.launch {
            callback(tetParkourElytsLinkPreferences(context).tetParkourElystLink.first())
        }

    fun tetRoukrapElytsFirebase(
        service: RuokRapTetRapRuokService,
        callback: (Boolean) -> Unit
    ) =viewModelScope.launch{
        tetRoukrapGistImpl(service).getTetRoukrapUrlSwitch { url,switch ->
            tetParkourElytsLink.tetRuokrapLinkOrganicAccess = switch
            tetParkourElytsLink.tetRuokrapLinkUrl = url
            callback(url.contains("jhfe".comparkourracegam()))
        }
    }

    fun tetParkourSwitchCheckOrganic() =
        tetParkourElytsLink.tetRuokrapLinkMediaSource == "qfspnzm".comparkourracegam() && tetParkourElytsLink.tetRuokrapLinkOrganicAccess == false


    fun aFTetParkourElytsIDSetAFID(id: String) {
        tetParkourElytsLink.tetRuokrapLinkAppsFlyerUserId = id
    }

    fun tetParkourElytsParkourSetStatusAF(value: String) {
        val tetParkourSwitch = "qfspnzm".comparkourracegam().replaceFirstChar { it.uppercase() }
        if (value == tetParkourSwitch && tetParkourElytsLink.tetRuokrapLinkDeepLink == null) {
            tetParkourElytsLink.tetRuokrapLinkMediaSource = "qfspnzm".comparkourracegam()
        }
    }

    fun tetParkourSetElytsSetAFCampaign(value: String) {
        tetParkourElytsLink.tetRuokrapLinkCampaign = value
        tetParkourElytsLink.tetRuokrapLinkCampaign?.let {
            tetParkourElytsLink.tetRuokrapLinkSubAll = it.split("_")
        }
    }

    fun channelAFTetParkourElytsSetAFChannel(value: String) {
        tetParkourElytsLink.tetRuokrapLinkAfChannel = value
    }

    fun mediaTetParkourSourceElytsSetAFMediaSource(value: String) {
        tetParkourElytsLink.tetRuokrapLinkMediaSource = value
    }

    private fun deepTetParkourElytsSetDeepLink(value: Uri?) {
        tetParkourElytsLink.tetRuokrapLinkDeepLink = value?.toString()
        tetParkourElytsLink.tetRuokrapLinkDeepLink?.let {
            val parkRuokrapTetArray = it.split("//")
            tetParkourElytsLink.tetRuokrapLinkSubAll = parkRuokrapTetArray[1].split("_")
        }
    }

    fun linkBuildTetRoukrapBuildLink(context: Context, callback: (String) -> Unit) =
        viewModelScope.launch {
            val stringUrlTetParkourElytsLinkString =
                tetParkourElytsLink.tetRuokrapLinkCollect(context)
            tetParkourElytsLinkPreferences(context).tetParkourElytsSaveLink(
                stringUrlTetParkourElytsLinkString
            )
            callback(stringUrlTetParkourElytsLinkString)
        }

    fun ruokrapTetFBGOOGOLinkWOrkId(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        val wadTetParkourGootetParkourStylesLoadinggleID =
            AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
        tetParkourElytsLink.tetRuokrapLinkGoogleId = wadTetParkourGootetParkourStylesLoadinggleID
        OneSignal.setExternalUserId(wadTetParkourGootetParkourStylesLoadinggleID)

        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(context) {
            deepTetParkourElytsSetDeepLink(it?.targetUri)
        }
    }
}