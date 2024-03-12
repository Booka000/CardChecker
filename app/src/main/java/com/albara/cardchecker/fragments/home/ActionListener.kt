package com.albara.cardchecker.fragments.home

import com.albara.cardchecker.data.objects.Card

interface OnItemClickListener {
    fun onItemClickListener(card: Card)
    fun onItemLongClick(card: Card)
}
