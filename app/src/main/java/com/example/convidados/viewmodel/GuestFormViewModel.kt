package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository


/* 4.0 Criando repo
- A primeira coisa a ser feita é a criação do GuestRpository
Continuar em 4.1 em GuestRepository
    6.1 Conectando pacotes
- Agora a ViewModel também precisa receber o contexto para poder chamar o metodo getInstance, mas nós
não conseguimos acessar o construtor da viewModel, pois quem faz essa itermediação é
ViewModelProvider(this).get(GuestFormViewModel::class.java)
desta forma o ideal é usar a interface AndroidViewModel, no lugar de ViewModel. Passando o parametro
Applicantion (que faz o papel do contexto, dado pela viewModelProvider) no construtor da classe
GuestFormViewModel
- Como transitar os dados da activity para viewmodel e da viewmodel para repository. Posso criar um
meotodo Save na viewModel e para retornar o ideal é retornar um classe com todas as caracteriticas
necessarias, por isso criamos o GuestModel, o modelo é quem tem os dados.
 */

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    fun save(guest: GuestModel){
        if (guest.id == 0) {
            repository.insert(guest)
        } else {
            repository.update(guest)
        }
    }


    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }



}