package com.albara.cardchecker.data.objects

import androidx.room.ColumnInfo

data class Number(
    @ColumnInfo(name = "card_length")
    val  length: Int?,
    @ColumnInfo(name = "card_luhn")
    val  luhn: Boolean?
)