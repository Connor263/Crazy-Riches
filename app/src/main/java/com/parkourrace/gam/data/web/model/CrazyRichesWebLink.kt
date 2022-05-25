package com.parkourrace.gam.data.web.model

import android.content.Context
import com.parkourrace.gam.R
import com.parkourrace.gam.utils.crazyRichesVigenere


data class CrazyRichesWebLink(
    var crazyRichesGoogleId: String? = null,
    var crazyRichesAppsFlyerUserId: String? = null,
    var crazyRichesSubAll: List<String> = listOf("", "", "", "", "", "", "", "", "", ""),
    var crazyRichesDeepLink: String? = null,
    var crazyRichesMediaSource: String? = null,
    var crazyRichesAfChannel: String? = null,
    var crazyRichesCampaign: String? = null,
    var crazyRichesUrl: String? = null,
    var crazyRichesOrganicAccess: Boolean? = null
) {
    fun crazyRichesCollect(context: Context): String {
        val crazyRichesResources = context.resources
        val crazyRichesPackageName = context.packageName
        val crazyRichesAppsFlyerDevKey = crazyRichesResources.getString(R.string.apps_dev_key)
        val crazyRichesFbToken = crazyRichesResources.getString(R.string.fb_at)
        val crazyRichesFbAppId = crazyRichesResources.getString(R.string.fb_app_id)

        val crazyRichesStringMediaSource = "?ospxa_jyiltv=".crazyRichesVigenere()
        val crazyRichesStringGoogleId = "&icavlv_krcu=".crazyRichesVigenere()
        val crazyRichesStringAppsFlyerUserId = "&ct_gheisr=".crazyRichesVigenere()
        val crazyRichesStringPackageName = "&dizslv=".crazyRichesVigenere()
        val crazyRichesStringAppsFlyerDevKey = "&fsh_zep=".crazyRichesVigenere()
        val crazyRichesStringFbToken = "&hp_mi=".crazyRichesVigenere()
        val crazyRichesStringFbAppId = "&hp_mep_zn=".crazyRichesVigenere()
        val crazyRichesStringAfChannel = "&ct_owaexsf=".crazyRichesVigenere()
        val crazyRichesStringCampaign = "&eoyeazqb=".crazyRichesVigenere()


        var crazyRichesIndex = 0
        val crazyRichesSubsString = crazyRichesSubAll.joinToString(separator = "") {
            crazyRichesIndex++
            "&sub$crazyRichesIndex=$it"
        }

        return "${this.crazyRichesUrl}" +
                "$crazyRichesStringMediaSource${this.crazyRichesMediaSource}" +
                "$crazyRichesStringGoogleId${this.crazyRichesGoogleId}" +
                "$crazyRichesStringAppsFlyerUserId${this.crazyRichesAppsFlyerUserId}" +
                "$crazyRichesStringPackageName$crazyRichesPackageName" +
                "$crazyRichesStringAppsFlyerDevKey$crazyRichesAppsFlyerDevKey" +
                "$crazyRichesStringFbToken$crazyRichesFbToken" +
                "$crazyRichesStringFbAppId$crazyRichesFbAppId" +
                "$crazyRichesStringAfChannel${this.crazyRichesAfChannel}" +
                "$crazyRichesStringCampaign${this.crazyRichesCampaign}" +
                crazyRichesSubsString
    }
}