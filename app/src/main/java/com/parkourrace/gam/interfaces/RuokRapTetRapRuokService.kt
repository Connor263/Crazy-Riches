package com.parkourrace.gam.interfaces

import com.parkourrace.gam.data.web.model.RuokRapTetRapRuokModel
import retrofit2.http.GET

interface RuokRapTetRapRuokService {
    @GET("505a646ac46c3954ef2ef7dd67313723/raw/8303016d8224122a0b0f69879d1e97fe3ec7606e/lvrtsocuh.oqtlcnuqitasnbyph.kwmgwznmv263")
    suspend fun ruokRapTetRapGetGistRuokService(): RuokRapTetRapRuokModel
}