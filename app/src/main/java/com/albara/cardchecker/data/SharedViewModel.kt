package com.albara.cardchecker.data

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albara.cardchecker.data.localDB.CardDatabase
import com.albara.cardchecker.data.objects.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val cards : LiveData<List<Card>>
    private val repository : CardRepository

    private val _errorMessage by lazy { MutableLiveData<String>() }
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _card by lazy { SingleLiveEvent<Card>()}
    val card : SingleLiveEvent<Card> get() = _card


    init {
        val cardDao = CardDatabase.getInstance(application).cardDao()
        repository = CardRepository(cardDao)
        cards = repository.getAllCards()
    }

    fun getCardByBIN(BIN: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val temp = cards.value?.find { card -> card.BIN == BIN}
            temp?.let {
                _card.postValue(it)
                return@launch
            }
            try {
                val cardInfo = repository.fetchCardInfo(BIN)
                cardInfo.apply {
                    val card = Card(0, BIN, this)
                    repository.addCard(card)
                    _card.postValue(card)
                }
            } catch (e : Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun setCard(card: Card){
        _card.value = card
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteCard(card) }
    }

    fun blaBla(@MainThread callback : (result : String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            callback("blabla")
        }
    }
}