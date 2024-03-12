package com.albara.cardchecker.data.api

import com.albara.cardchecker.data.objects.CardInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CardApi {
    @GET
    suspend fun getCardInfo(@Url BIN : String) : Response<CardInfo>
}