package com.parkourrace.gam.di

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.parkourrace.gam.R
import com.parkourrace.gam.interfaces.RuokRapTetRapRuokService
import com.parkourrace.gam.utils.comparkourracegam
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TetParkourElytsApplication : Application() {

    lateinit var ruokRapTetRapRuokService: RuokRapTetRapRuokService
    private lateinit var ruokRaFitpTetRetroWfaRauoTETParkours: Retrofit

    override fun onCreate() {
        super.onCreate()
        styleurElytsInitAppsFlyer()
        iTetplTetParkourEonRetroTetPsApplonFit()
    }

    private fun iTetplTetParkourEonRetroTetPsApplonFit() {
        ruokRaFitpTetRetroWfaRauoTETParkours = Retrofit.Builder()
            .baseUrl("jhfes://xsgn.xztjyhuegfodnkobn.tfm/estnat263/".comparkourracegam())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        rapTetApplicationTetRapRuokService()
    }

    private fun rapTetApplicationTetRapRuokService() {
        ruokRapTetRapRuokService =
            ruokRaFitpTetRetroWfaRauoTETParkours.create(RuokRapTetRapRuokService::class.java)
    }

    private fun styleurElytsInitAppsFlyer() {
        AppsFlyerLib.getInstance()
            .init(resources.getString(R.string.apps_dev_key), ruokRaFitpTetRetroarListener, this)
        AppsFlyerLib.getInstance().start(this)
        tetParkourElytsInitOneSADsignal()
    }

    private fun tetParkourElytsInitOneSADsignal() {
        OneSignal.initWithContext(this)
        OneSignal.setAppId(resources.getString(R.string.one_signal_key))
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    private val ruokRaFitpTetRetroarListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            data?.let {
                tetParkoruokRfaRauoTytsAppsFlyerState.postValue(it)
            }
        }

        override fun onConversionDataFail(p0: String?) {}

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

        override fun onAttributionFailure(p0: String?) {}

    }

    companion object {
        val tetParkoruokRfaRauoTytsAppsFlyerState = MutableLiveData<MutableMap<String, Any>>()
    }
}