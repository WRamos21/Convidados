package com.example.convidados.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel

/* A viewHolder é quem possui a referencia para os elementos de interface, em itemView é onde encontramos
Botões, textViews etc.
- Em Bind é onde geramos ligação do elemento de interface com os dados, fazendo essa ligação a cada
GuestModel. O construtor da classe foi alterado para receber um rowGuestBinding, preciso usar val
no construtor pois No Kotlin, quando você declara uma variável no construtor de uma classe, ela só
se torna um membro da classe (acessível em todas as funções)
 */


class GuestsViewHolder(private val rowGuestBinding: RowGuestBinding) :
    RecyclerView.ViewHolder(rowGuestBinding.root) {

    fun bind(guest: GuestModel) {
        rowGuestBinding.textName.text = guest.name
    }
}