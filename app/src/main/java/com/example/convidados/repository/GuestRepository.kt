package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.model.GuestModel


/* 4.1 Criando repo (manipulção do dados)
- Quando vou intanciar um banco de dados dentro da classe, toda vez que ela for intanciada o banco de
dados também será, isso pode acarretar em problemas como em ler um dado que está sendo alterado
por isso é muito comun utilizar um Singleton, responsavel por controlar as instancias da classe.
Para isso precisamos impedir que a classe seja instanciada, utilizando private constuctor.
o Companion Object serve para acessar metodos sem precisar instanciar a classe>
A função getInstance retorna uma intancia do repositorio, mesmo que eu chame essa função mais de 2 vez
o GuestRepository nunca vai ser instanciado mais de uma vez, isso por que sempre que já estiver inicializado
retornamos a instancia deste mesmo repo.
Continua em 5.0 em GuestDataBase
    6.0 Conectando pacotes
- Agora podemos instanciar o DataBase, mas ele precisa de um contexto, podemos passar o contexto ao construtor
com isso getInstance também vai precisar receber um context no construtor
continua em 6.1 em GuestFormViewModel
    7.0 Inserção no banco de dados
- Para escrever no banco de dados podemos usar writable.  db.isert utiliza nome da tabela, nullComumnHack
é usado para passar um valor caso todos os valores inseridos sejam nulos, oq não acontece no nosso caso,
pois mesmo que não seja informado nada o ID será implementado com inteiro pelo proprio banco de dados.
além disso db.isert também pede um values, da classe contentValues que carrega as informações para o banco.
nele podemos usar put para dizer quais os dados que serão levados.
continua em 7.1 em GuestFormActivity em onClik
-podemos ainda se prevenir das exceções utilizando o try catch
 */

class GuestRepository private constructor(context: Context){

    private val guestDataBase = GuestDataBase(context)

    //Singleton
    companion object{
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put("name", guest.name)
            values.put("presence", presence)

            db.insert("Guest", null, values)
            return true
        } catch (e: Exception){
            return false
        }
    }
}