package com.parkourrace.gam.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.gson.internal.GsonBuildConfig
import com.onesignal.OneSignal
import com.parkourrace.gam.R
import com.parkourrace.gam.interfaces.RuokRapTetRapRuokService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class TetParkourElytsApplication : Application() {

    lateinit var ruokRapTetRapRuokService: RuokRapTetRapRuokService

    override fun onCreate() {
        super.onCreate()
        tetParkourElytsInitAppsFlyer()
        tetParkourElytsInitOneSignal()

        val retroTetParkourElytsApplication = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        ruokRapTetRapRuokService =
            retroTetParkourElytsApplication.create(RuokRapTetRapRuokService::class.java)
    }

    private fun tetParkourElytsInitAppsFlyer() {
        AppsFlyerLib.getInstance()
            .init(resources.getString(R.string.apps_dev_key), crazyListener, this)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun tetParkourElytsInitOneSignal() {
        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.one_signal_key))
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    private val crazyListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            data?.let {
                tetParkourElytsAppsFlyerState.postValue(it)
            }
        }

        override fun onConversionDataFail(p0: String?) {}

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

        override fun onAttributionFailure(p0: String?) {}

    }

    companion object {
        val tetParkourElytsAppsFlyerState = MutableLiveData<MutableMap<String, Any>>()
    }
}