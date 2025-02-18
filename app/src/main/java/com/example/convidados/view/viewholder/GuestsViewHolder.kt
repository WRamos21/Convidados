package com.example.convidados.view.viewholder

import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

/* A viewHolder é quem possui a referencia para os elementos de interface, em itemView é onde encontramos
Botões, textViews etc.
- Em Bind é onde geramos ligação do elemento de interface com os dados, fazendo essa ligação a cada
GuestModel. O construtor da classe foi alterado para receber um rowGuestBinding, preciso usar val
no construtor pois No Kotlin, quando você declara uma variável no construtor de uma classe, ela só
se torna um membro da classe (acessível em todas as funções)
- Quero fazer um evento de click dentro da ViewHolder, mas não é de resposabilidade dela de responder
pelos click, pois ela não tem o  oclontrole do ciclo de vida do android, quem deve fazer isso é a
allGuestFragment, pois supondo que vamos remover um convidado, essa fragment já tem todos os metodos
para atualizar a lista.
- Passando como paramentro um OnguestListenar (que é uma interface criada) que contém os metodos
onCiick e onDelete, mas quando formos instanciar GuestViewHolder temos que fornecer qual vai ser a
função do metodo onClick. Desta forma a viewHolder não precisa se preocupar com a resposata do click
- Por esta razão precisamos ter este OnGuestListener no adapter
Continua em allGuestsAdapter 15.0 Eventos de Listagem

    17.0 Confirmação de remoção
- Quem vai fazer a confimação da remoção é o alertDialog, builder constroi a alert, que pde um contex
mas que está na RecyclerView (chamado itemView.Context)
- Posso colocar botões de confimação como positiveButton, neste caso pedirá um DialogInterface que
implementamos anonimente
 */


class GuestsViewHolder(
    private val rowGuestBinding: RowGuestBinding,
    private val listerner: OnGuestListener
) :
    RecyclerView.ViewHolder(rowGuestBinding.root) {

    fun bind(guest: GuestModel) {
        rowGuestBinding.textName.text = guest.name

        rowGuestBinding.textName.setOnClickListener {
            listerner.onClik(guest.id)
        }

        rowGuestBinding.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de convidado")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("Sim", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        listerner.onDelete(guest.id)
                    }
                })
                .setNegativeButton("Não", null)
                .create().show()
            true //setOnLongClickListener exige que você retorne true ou false
        }

    }
}