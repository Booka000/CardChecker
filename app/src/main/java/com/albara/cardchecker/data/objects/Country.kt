package com.albara.cardchecker.data.objects

import androidx.room.ColumnInfo

data class Country(
    @ColumnInfo(name = "country_alpha")
    val  alpha2: String?,
    @ColumnInfo(name = "country_currency")
    val  currency: String?,
    @ColumnInfo(name = "country_emoji")
    val  emoji: String?,
    @ColumnInfo(name = "country_latitude")
    val  latitude: Int?,
    @ColumnInfo(name = "country_longitude")
    val  longitude: Int?,
    @ColumnInfo(name = "country_name")
    val  name: String?,
    @ColumnInfo(name = "country_numeric")
    val  numeric: String?
)