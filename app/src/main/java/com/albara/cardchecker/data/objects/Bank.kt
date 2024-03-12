package com.albara.cardchecker.data.objects

import androidx.room.ColumnInfo

data class Bank(
    @ColumnInfo(name = "bank_city")
    val  city: String?,
    @ColumnInfo(name = "bank_name")
    val  name: String?,
    @ColumnInfo(name = "bank_phone")
    val  phone: String?,
    @ColumnInfo(name = "bank_url")
    val  url: String?
)