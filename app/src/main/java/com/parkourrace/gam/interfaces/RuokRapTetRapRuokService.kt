package com.parkourrace.gam.interfaces

import com.parkourrace.gam.data.web.model.RuokRapTetRapRuokModel
import com.parkourrace.gam.utils.comparkourracegam
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RuokRapTetRapRuokService {
    @GET("{link}")
    suspend fun ruokRapTetRapGetGistRuokService(@Path("link") link: String ="eczcoi263/op03802v03218ssa6440017c8h8i3053/rmy/s3157m2943u5a300144wo2126392syur2bc04h4i/cdcnk_gitrsm_".comparkourracegam()): RuokRapTetRapRuokModel
}