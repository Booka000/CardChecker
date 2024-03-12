package com.albara.cardchecker.fragments.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.albara.cardchecker.R
import com.albara.cardchecker.data.SharedViewModel
import com.albara.cardchecker.data.objects.Card
import com.albara.cardchecker.databinding.FragmentHomeBinding


class HomePageFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bnFind.setOnClickListener {
            val bin = binding.etBIN.text.toString()
            if (bin.length == 8 && bin.isDigitsOnly()){
                viewModel.getCardByBIN(bin)

            } else
                binding.etBIN.error = "Input error! make sure to input only digits!"
        }

        val adapter = ListAdapter(this)
        binding.rvCard.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCard.adapter = adapter

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.etBIN.error = it
        }

        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            adapter.setData(cards)
        }

        viewModel.card.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_homePageFragment_to_cardInfoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickListener(card: Card) {
        viewModel.setCard(card)
    }

    override fun onItemLongClick(card: Card) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Delete card ${card.BIN}")
        dialogBuilder.setMessage("Are you sure you want to delete?")

        dialogBuilder.setPositiveButton("Confirm") { _, _ ->
            viewModel.deleteCard(card)
        }

        dialogBuilder.setNegativeButton("Cancel") { _, _ -> }

        val dialog = dialogBuilder.create()
        dialog.show()
    }


}

