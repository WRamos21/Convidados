package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository


/* 12.1 Chamando listagem através da viewmodel
- Como eu preciso saber qual a intancia do repositorio, que por sua vez precisa de um contexto, é
necessario utilizar AndroidViewModel para usar o seu contexto, já que viewModel não possuem context.
- getAll da viewModel só serve para chamar a função getAll do repository
- listAllGuests (instância de MutableLiveData) é privada, ou seja, apenas o ViewModel pode modificar
seus valores.
- A variável guests (de tipo LiveData) é pública e expõe os dados para serem observados, mas não pode
ser alterada externamente.
Os componentes da UI (como Activity ou Fragment) só têm acesso à LiveData. Isso garante que eles não
podem modificar os dados diretamente, evitando bugs ou comportamento inesperado.

 */
class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = GuestRepository(application.applicationContext)

    private val listAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listAllGuests

    fun getAll() {
        listAllGuests.value = repository.getAll()
    }

    fun delete(id: Int){
        repository.delete(id)
    }
}