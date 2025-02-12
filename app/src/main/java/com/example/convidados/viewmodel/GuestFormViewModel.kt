package com.example.convidados.viewmodel

import androidx.lifecycle.ViewModel
import com.example.convidados.repository.GuestRepository


/* 4.0 Criando repo
- A primeira coisa a ser feita é a criação do GuestRpository
Continuar em 4.1 em GuestRepository
 */

class GuestFormViewModel: ViewModel() {

    private val repository = GuestRepository.getInstance()


}