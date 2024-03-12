package com.albara.cardchecker.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.albara.cardchecker.data.SharedViewModel
import com.albara.cardchecker.R
import com.albara.cardchecker.databinding.FragmentCardInfoBinding
import com.albara.cardchecker.data.objects.CardInfo

class CardInfoFragment : Fragment() {

    private lateinit var viewModel : SharedViewModel

    private var _binding : FragmentCardInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.card.value?.let {
            displayCardInfo(it.cardInfo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private fun displayCardInfo(cardInfo : CardInfo) {

        if(cardInfo.scheme == "visa")
            binding.ivScheme.setImageResource(R.drawable.visa)
        else if (cardInfo.scheme == "mastercard")
            binding.ivScheme.setImageResource(R.drawable.mastercard)

        val type = SpannableString("Debit/Credit")
        when(cardInfo.type){
            "debit" -> {
                type.setSpan(ForegroundColorSpan(Color.BLACK),0,5,0)
                type.setSpan(StyleSpan(Typeface.BOLD), 0, 5,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                type.setSpan(ForegroundColorSpan(Color.GRAY),6,type.length,0)
            } "credit" -> {
                type.setSpan(ForegroundColorSpan(Color.GRAY),0,5,0)
                type.setSpan(ForegroundColorSpan(Color.BLACK),6,type.length,0)
                type.setSpan(StyleSpan(Typeface.BOLD), 6, type.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else -> {
                type.setSpan(ForegroundColorSpan(Color.GRAY),0,type.length,0)
            }
        }
        binding.tvType.setTextToGray(type)
        binding.tvBrand.setTextToGray(cardInfo.brand)
        binding.tvLength.setTextToGray(cardInfo.number.length?.toString()?: "?")
        val luhn = SpannableString("Yes/No")
        when(cardInfo.number.luhn){
            true -> {
                luhn.setSpan(ForegroundColorSpan(Color.BLACK),0,3,0)
                luhn.setSpan(StyleSpan(Typeface.BOLD), 0, 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                luhn.setSpan(ForegroundColorSpan(Color.GRAY),4,6,0)
            } false -> {
                luhn.setSpan(ForegroundColorSpan(Color.GRAY),0,3,0)
                luhn.setSpan(ForegroundColorSpan(Color.BLACK),4,6,0)
                luhn.setSpan(StyleSpan(Typeface.BOLD), 4, 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else ->{
                luhn.setSpan(ForegroundColorSpan(Color.GRAY),0,6,0)
            }
        }
        binding.tvLuhn.setTextToGray(luhn)

        val prepaid = SpannableString("Yes/No")
        when(cardInfo.number.luhn) {
            true -> {
                prepaid.setSpan(ForegroundColorSpan(Color.BLACK), 0, 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                prepaid.setSpan(StyleSpan(Typeface.BOLD), 0, 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                prepaid.setSpan(ForegroundColorSpan(Color.GRAY), 4, 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } false -> {
                prepaid.setSpan(ForegroundColorSpan(Color.GRAY), 0, 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                prepaid.setSpan(ForegroundColorSpan(Color.BLACK), 4, 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                prepaid.setSpan(StyleSpan(Typeface.BOLD), 4, 6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else -> {
                prepaid.setSpan(ForegroundColorSpan(Color.GRAY), 0, 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        binding.tvPrepaid.setTextToGray(prepaid)
        binding.tvBankName.setTextToGray(cardInfo.bank.name)

        val bank = "${cardInfo.bank.name}, ${cardInfo.bank.city}"
        binding.tvBankInfo.setTextToGray(bank)

        binding.tvBankInfo.setTextToGray(cardInfo.bank.url)
        binding.tvPhone.setTextToGray(cardInfo.bank.phone)


        val country = "${cardInfo.country.emoji} ${cardInfo.country.name}"
        binding.tvCountry.setTextToGray(country)

        if(cardInfo.country.latitude != null){
            val latitude = SpannableString(cardInfo.country.latitude.toString())
            val longitude = SpannableString(cardInfo.country.longitude.toString())
            latitude.setSpan(ForegroundColorSpan(Color.BLACK),0,latitude.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            longitude.setSpan(ForegroundColorSpan(Color.BLACK),0,longitude.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvCoordinates.text = ""
            binding.tvCoordinates.append("(latitude: ")
            binding.tvCoordinates.append(latitude)
            binding.tvCoordinates.append(", longitude: ")
            binding.tvCoordinates.append(longitude)
            binding.tvCoordinates.append(")")
        } else {
            val coordinates =  "(latitude: ?, longitude: ?)"
            binding.tvCoordinates.text = coordinates
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun TextView.setTextToGray(text : CharSequence?){
        if(text != null){
            this.text = text
        } else {
            this.text = "?"
            this.setTextColor(Color.GRAY)
        }
    }
}