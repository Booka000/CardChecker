package com.albara.cardchecker.data

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.albara.cardchecker.data.api.RetrofitInstance
import com.albara.cardchecker.data.localDB.CardDao
import com.albara.cardchecker.data.objects.Card
import com.albara.cardchecker.data.objects.CardInfo
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class CardRepository(private val cardDao: CardDao) {

    suspend fun addCard(card: Card) {
        cardDao.insertCard(card)
    }

    suspend fun fetchCardInfo(BIN: String) : CardInfo {
        try {
            RetrofitInstance.api.getCardInfo(BIN)
        } catch (e: IOException) {
            throw Exception("No internet connection")
        } catch (E: HttpException) {
            throw Exception("Unexpected response")
        }.also {
            it.body()?.run {
                if(it.isSuccessful)
                    return this
            }
        }
        throw Exception("Unexpected response")
    }

    fun getAllCards(): LiveData<List<Card>> {
        return cardDao.getCards()
    }

    suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card)
    }
}