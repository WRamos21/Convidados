package com.example.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.viewholder.GuestsViewHolder


/* 13.0 Listagem de Convidados usando RecyclerView
- Aqui precisamos extender essa classe com a RecyclerView.Adapter, que necessita de uma
outra classe abstrata (Impossivel de instanciar) VH (ViewHolder), por isso também precisamos criar
uma classe que herde de ViewHolder.
- Criamos uma variavel para armazenar a lista de convidados que vai receber os dados quando
updateGuest for utilizado, passando como paramentro a lista observada da ViewModel
- getItemCount diz o tamanho da listagem
- onCreate faz a criação do layout para cada item, por isso precisamos inflar (criar uma instancia de
layout XML) o layout row_guest,
como estamos em uma recyclerView não temos diretamente o layoutinflator. Mas parent (o ViewGroup,
fornecido automaticamente pela RecyclerView ,que representa o contêiner do item) me permite pegar
o layout inflator e o context, por isso inflamos apartir do parent
Para inflar precisamos do LayoutInflter, do parent e AttachToParent (true associa o layout
diretamente à RecyclerView. Escolhemos false para que quem controle isso seja o GuestAdapter
Essa função retorna um GuestsViewHolder (quem possui a referencia para os elementos de interface), que
precisa de uma View para ser instanciada, que no caso é  a view raiz (ConstraintLayout ou LinearLayout)
do layout Row_Guests
- onBindingViewHolder é chamada para Preencher as views do ViewHolder com os dados específicos da posição
Configurar listeners e comportamentos para o item, Atualizar a aparência do item baseado no estado dos dados
Como viewHolder é quem aramazena os elementos de interface, criamos um metodo "bind" para gerar ligação
do elemento de interface com os dados, fazendo essa ligação a cada GuestModel


 */
class GuestsAdapter : RecyclerView.Adapter<GuestsViewHolder>() {
    private var guestList: List<GuestModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestsViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestsViewHolder(item)
    }

    override fun onBindViewHolder(holder: GuestsViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged() // Manda a ReciclyrView se atualizar
    }
}