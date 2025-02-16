package com.example.convidados.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.view.viewholder.GuestsViewHolder

/* 13.0 Listagem de Convidados usando RecyclerView
- Aqui precisamos extender essa classe com a RecyclerView.Adapter, que necessita de uma
outra classe abstrata (Impossivel de instanciar) VH (ViewHolder), por isso também precisamos criar
uma classe que herde de ViewHolder.
- onCreate faz a criação do layout para cada item
- onBind faz a cola atribuind valores para o layout
- getItemCount diz o tamanho da listagem
 */

class GuestsAdapter: RecyclerView.Adapter<GuestsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: GuestsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}