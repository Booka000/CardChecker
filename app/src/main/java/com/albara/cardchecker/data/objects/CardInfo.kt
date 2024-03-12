package com.albara.cardchecker.data.objects

import androidx.room.ColumnInfo
import androidx.room.Embedded


data class CardInfo(
    @Embedded
    val  bank: Bank,
    @ColumnInfo(name = "card_brand")
    val  brand: String?,
    @Embedded
    val  country: Country,
    @Embedded
    val  number: Number,
    @ColumnInfo(name = "card_prepaid")
    val  prepaid: Boolean?,
    @ColumnInfo(name = "card_scheme")
    val  scheme: String?,
    @ColumnInfo(name = "card_type")
    val  type: String?
)