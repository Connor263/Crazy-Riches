package com.parkourrace.gam

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onesignal.OneSignal
import com.parkourrace.gam.data.web.model.CrazyRichesWebLink
import com.parkourrace.gam.data.web.preferences.CrazyRichesLinkPreferencesDataStore
import com.parkourrace.gam.utils.crazyRichesVigenere
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

const val TAG = "TAG"

class MainViewModel : ViewModel() {
    var crazyRichesRouteString = mutableStateOf("")

    val crazyRichesLoading = mutableStateOf(true)

    val crazyRichesPreferences: (Context) -> CrazyRichesLinkPreferencesDataStore =
        { CrazyRichesLinkPreferencesDataStore(it) }


    private val crazyRichesLink = CrazyRichesWebLink()

    fun crazyRichesGetCacheLink(context: Context, callback: (String) -> Unit) = viewModelScope.launch {
        callback(crazyRichesPreferences(context).crazyRichesLink.first())
    }

    fun crazyRichesSetUrlAndOrganic(url: String, organic: Boolean, callback: (Boolean) -> Unit) {
        crazyRichesLink.crazyRichesUrl = url
        crazyRichesLink.crazyRichesOrganicAccess = organic
        callback(url.contains("jhfe".crazyRichesVigenere()))
        Log.d(TAG, "crazyRichesSetUrlAndOrganic: $url $organic")
    }

    fun checkOrganic() =
        crazyRichesLink.crazyRichesMediaSource == "qfspnzm".crazyRichesVigenere() && crazyRichesLink.crazyRichesOrganicAccess == false

    fun crazyRichesSetGoogleID(id: String) {
        crazyRichesLink.crazyRichesGoogleId = id
        OneSignal.setExternalUserId(id)
        Log.d(TAG, "crazyRichesSetGoogleID: $id")
    }

    fun crazyRichesSetAFID(id: String) {
        crazyRichesLink.crazyRichesAppsFlyerUserId = id
        Log.d(TAG, "crazyRichesSetAFID: $id")
    }

    fun crazyRichesSetAFStatus(value: String) {
        val crazyRichesOrganic = "qfspnzm".crazyRichesVigenere().replaceFirstChar { it.uppercase() }
        if (value == crazyRichesOrganic && crazyRichesLink.crazyRichesDeepLink == null) {
            crazyRichesLink.crazyRichesMediaSource = "qfspnzm".crazyRichesVigenere()
        }
        Log.d(TAG, "crazyRichesSetAFStatus: $value")
    }

    fun crazyRichesSetAFCampaign(value: String) {
        crazyRichesLink.crazyRichesCampaign = value
        crazyRichesLink.crazyRichesCampaign?.let {
            crazyRichesLink.crazyRichesSubAll = it.split("_")
        }
        Log.d(TAG, "crazyRichesSetAFCampaign: campaign $value")
        Log.d(TAG, "crazyRichesSetAFCampaign: subAll $value")
    }

    fun crazyRichesSetAFChannel(value: String) {
        crazyRichesLink.crazyRichesAfChannel = value
        Log.d(TAG, "crazyRichesSetAFChannel: $value")
    }

    fun crazyRichesSetAFMediaSource(value: String) {
        crazyRichesLink.crazyRichesMediaSource = value
        Log.d(TAG, "crazyRichesSetMediaSource: $value")
    }

    fun crazyRichesSetDeepLink(value: Uri?) {
        crazyRichesLink.crazyRichesDeepLink = value?.toString()
        crazyRichesLink.crazyRichesDeepLink?.let {
            val crazyRichesArray = it.split("//")
            crazyRichesLink.crazyRichesSubAll = crazyRichesArray[1].split("_")
        }
        Log.d(TAG, "crazyRichesSetDeepLink: deepLink $value")
        Log.d(TAG, "crazyRichesSetDeepLink: subAll ${crazyRichesLink.crazyRichesSubAll}")
    }

    fun crazyRichesCollectLink(context: Context, callback: (String) -> Unit) = viewModelScope.launch {
        val crazyRichesLink = crazyRichesLink.crazyRichesCollect(context)
        crazyRichesPreferences(context).crazyRichesSaveLink(crazyRichesLink)
        callback(crazyRichesLink)
    }
}