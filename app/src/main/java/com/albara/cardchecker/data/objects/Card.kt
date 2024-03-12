package com.albara.cardchecker.data.objects

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardTable")
data class Card (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val BIN : String,
    @Embedded
    val cardInfo : CardInfo
    )