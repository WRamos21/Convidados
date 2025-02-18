package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
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
- Dentro de onObserve temos a variavel publica imutavel que contém a lista de GuestModels, de forma
que quando passamos guests update, estamos atualizando os dados presentes no Adapter.
    15.0 Eventos de Listagem
- Vamos fazer uma instancia anonima do OnGuestListener. Uma instância anônima em Kotlin é um objeto
criado sem uma classe explicitamente nomeada. É uma forma de criar objetos únicos que implementam
uma interface ou herdam de uma classe diretamente no ponto em que são necessários, sem precisar
declarar uma classe separada.
Ao invés de criar uma nova classe para implementar uma interface ou herdar de uma classe, você usa o
object em Kotlin para criar uma instância no mesmo local. O objeto anônimo não tem nome, mas pode
ter seu comportamento personalizado diretamente no local onde é criado.
    18.0 Atualização de convidado
- Quando clico no nome quero abrir a activiy de formulario novamente, mas posso passar informações
extras para a activity, neste caso eu envio um bundle que guarda uma chave e um valor. Desta forma
posso diferencia a criação desta activity pois ela tem informações adicionais que não temos
na guestForm padrão.
continua em GuestFormActivity

 */


class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null
    private val binding get() = _binding!!
    private val adapter = GuestsAdapter()
    private lateinit var viewModel: AllGuestsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(AllGuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        // Definindo Layout da RecyclerView
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        // Adapter
        binding.recyclerAllGuests.adapter = adapter

        val listener = object : OnGuestListener{
            override fun onClik(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java )
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }
        }

        adapter.attachListener(listener)

        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}