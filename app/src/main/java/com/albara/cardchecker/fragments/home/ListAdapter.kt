package com.albara.cardchecker.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.albara.cardchecker.R
import com.albara.cardchecker.data.objects.Card
import com.albara.cardchecker.databinding.RvRowItemBinding

class ListAdapter(private val actionListener: OnItemClickListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var cards = emptyList<Card>()

    inner class ViewHolder(private val binding : RvRowItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    actionListener.onItemClickListener(cards[position])
                }
            }

            binding.root.setOnLongClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    actionListener.onItemLongClick(cards[position])
                }
                true
            }
        }

        fun bind (card : Card) {
            if(card.cardInfo.scheme == "visa")
                binding.rvImageScheme.setImageResource(R.drawable.visa)
            else if (card.cardInfo.scheme == "mastercard")
                binding.rvImageScheme.setImageResource(R.drawable.mastercard)
            binding.rvBin.text = card.BIN
            binding.rvCountry.text = card.cardInfo.country.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvRowItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cards[position]
        holder.bind(currentItem)
    }

    fun setData(cards : List<Card>) {
        val diffResult = DiffUtil.calculateDiff(DataDiffCallback(this.cards, cards))
        this.cards = cards
        diffResult.dispatchUpdatesTo(this)
    }

    private class DataDiffCallback(private val oldList: List<Card>, private val newList: List<Card>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition].id == newList[newPosition].id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }
    }
}