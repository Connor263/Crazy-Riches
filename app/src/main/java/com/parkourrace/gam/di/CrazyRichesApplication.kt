package com.parkourrace.gam.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.parkourrace.gam.R

class CrazyRichesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        crazyRichesInitAppsFlyer()
        crazyRichesInitOneSignal()
    }

    private fun crazyRichesInitAppsFlyer() {
        AppsFlyerLib.getInstance()
            .init(resources.getString(R.string.apps_dev_key), crazyListener, this)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun crazyRichesInitOneSignal() {
        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.one_signal_key))
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    private val crazyListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            data?.let {
                crazyRichesAppsFlyerState.postValue(it)
            }
        }

        override fun onConversionDataFail(p0: String?) {}

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

        override fun onAttributionFailure(p0: String?) {}

    }

    companion object {
        val crazyRichesAppsFlyerState = MutableLiveData<MutableMap<String, Any>>()
    }
}