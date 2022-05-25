package com.parkourrace.gam.data.web.model

import android.content.Context
import com.parkourrace.gam.R
import com.parkourrace.gam.utils.comparkourracegam


data class TetRuokrapLink(
    var tetRuokrapLinkGoogleId: String? = null,
    var tetRuokrapLinkAppsFlyerUserId: String? = null,
    var tetRuokrapLinkSubAll: List<String> = listOf("", "", "", "", "", "", "", "", "", ""),
    var tetRuokrapLinkDeepLink: String? = null,
    var tetRuokrapLinkMediaSource: String? = null,
    var tetRuokrapLinkAfChannel: String? = null,
    var tetRuokrapLinkCampaign: String? = null,
    var tetRuokrapLinkUrl: String? = null,
    var tetRuokrapLinkOrganicAccess: Boolean? = null
) {
    fun tetRuokrapLinkCollect(context: Context): String {
        val tetRuokrapLinkResources = context.resources
        val tetRuokrapLinkPackageName = context.packageName
        val tetRuokrapLinkAppsFlyerDevKey = tetRuokrapLinkResources.getString(R.string.apps_dev_key)
        val tetRuokrapLinkFbToken = tetRuokrapLinkResources.getString(R.string.fb_at)
        val tetRuokrapLinkFbAppId = tetRuokrapLinkResources.getString(R.string.fb_app_id)

        val stringTetRuokrapLinkMediaSource = "?ospxa_jyiltv=".comparkourracegam()
        val stringTetRuokrapLinkGoogleId = "&icavlv_krcu=".comparkourracegam()
        val stringTetRuokrapLinkAppsFlyerUserId = "&ct_gheisr=".comparkourracegam()
        val stringTetRuokrapLinkPackageName = "&dizslv=".comparkourracegam()
        val stringTetRuokrapLinkAppsFlyerDevKey = "&fsh_zep=".comparkourracegam()
        val stringTetRuokrapLinkFbToken = "&hp_mi=".comparkourracegam()
        val stringTetRuokrapLinkFbAppId = "&hp_mep_zn=".comparkourracegam()
        val stringTetRuokrapLinkAfChannel = "&ct_owaexsf=".comparkourracegam()
        val stringTetRuokrapLinkCampaign = "&eoyeazqb=".comparkourracegam()


        var tetRuokrapLinkIndex = 0
        val tetRuokrapLinkSubsString = tetRuokrapLinkSubAll.joinToString(separator = "") {
            tetRuokrapLinkIndex++
            "&sub$tetRuokrapLinkIndex=$it"
        }

        return "${this.tetRuokrapLinkUrl}" +
                "$stringTetRuokrapLinkMediaSource${this.tetRuokrapLinkMediaSource}" +
                "$stringTetRuokrapLinkGoogleId${this.tetRuokrapLinkGoogleId}" +
                "$stringTetRuokrapLinkAppsFlyerUserId${this.tetRuokrapLinkAppsFlyerUserId}" +
                "$stringTetRuokrapLinkPackageName$tetRuokrapLinkPackageName" +
                "$stringTetRuokrapLinkAppsFlyerDevKey$tetRuokrapLinkAppsFlyerDevKey" +
                "$stringTetRuokrapLinkFbToken$tetRuokrapLinkFbToken" +
                "$stringTetRuokrapLinkFbAppId$tetRuokrapLinkFbAppId" +
                "$stringTetRuokrapLinkAfChannel${this.tetRuokrapLinkAfChannel}" +
                "$stringTetRuokrapLinkCampaign${this.tetRuokrapLinkCampaign}" +
                tetRuokrapLinkSubsString
    }
}