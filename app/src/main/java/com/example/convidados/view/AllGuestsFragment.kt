package com.example.convidados.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.viewmodel.AllGuestsViewModel

/* 12.0 Chamando listagem através da viewmodel
- Vamos criar um metodos para oservar a viewModel chamado observe, mas para isso a viewModel precisa
ter uma função para receber os convidados, por isso foi criado o metodo getAll nessa classe
    13.0 Listagem de Convidados usando RecyclerView
- A primeira coisa a ser feito é definir o layout da RecyclerView, layoutManager indica o
 layouyt da viewGroup criado pela recyclerView (não é o layout da listagem), aqui definimos com
 linearLayout
- Definimos também o adapter, que é uma classe abstrata, por isso criamos a classe GuestsAdapter,
O adapter é a intermediação entre os dados e o layout.
 */


class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllGuestsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        // Definindo Layout da RecyclerView
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        // Adapter
        binding.recyclerAllGuests.adapter = GuestsAdapter()

        viewModel.getAll()
        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
        }

    }
}