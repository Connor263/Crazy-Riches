package com.parkourrace.gam.data.web.repo

import android.content.Context
import android.util.Log
import com.parkourrace.gam.interfaces.RuokRapTetRapRuokService
import com.parkourrace.gam.interfaces.TetRoukrapGist
import com.parkourrace.gam.utils.comparkourracegam

class TetRoukrapGistImpl(
    private val service: RuokRapTetRapRuokService,
//private val context: Context,
) : TetRoukrapGist {


    override suspend fun getTetRoukrapUrlSwitch(callback: (String, Boolean) -> Unit) {
       val tetRoukrapGistImplResult = service.ruokRapTetRapGetGistRuokService()

        tetRoukrapGistImplResult.urlRuokRapTetRapRuokModel?.let { url ->
            tetRoukrapGistImplResult.switchRuokRapTetRapRuokModel?.let { switch ->
                callback(
                    url,
                    switch
                )
            }
        }
    }

    /*init {
        FirebaseApp.initializeApp(context)
    }

    private val tetRoukrapSettings =
        FirebaseRemoteConfigSettings.Builder()
            .setFetchTimeoutInSeconds(2500)
            .build()
    private val tetRoukrapINSTANCE = FirebaseRemoteConfig.getInstance().apply {
        setConfigSettingsAsync(tetRoukrapSettings)
    }

    override fun getTetRoukrapUrl(callback: (String) -> Unit) {
        tetRoukrapINSTANCE.fetchAndActivate().addOnCompleteListener {
            callback(tetRoukrapINSTANCE.getString(context.resources.getString(R.string.firebase_root_url)))
        }
    }

    override fun getTetRoukrapSwitch(callback: (Boolean) -> Unit) {
        tetRoukrapINSTANCE.fetchAndActivate().addOnCompleteListener {
            callback(
                tetRoukrapINSTANCE.getBoolean(
                    context.resources.getString(R.string.firebase_switch)
                )
            )
        }
    }*/
}