package com.albara.cardchecker.data.localDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albara.cardchecker.data.objects.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card : Card)

    @Delete
    suspend fun deleteCard(card : Card)

    @Query("SELECT * FROM cardTable ORDER BY id ASC")
    fun getCards() : LiveData<List<Card>>


}