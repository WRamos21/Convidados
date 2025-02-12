package com.example.convidados.repository


/* 4.1 Criando repo
- Quando vou intanciar um banco de dados dentro da classe, toda vez que ela for intanciada o banco de
dados também será, isso pode acarretar em problemas como em ler um dado que está sendo alterado
por isso é muito comun utilizar um Singleton, responsavel por controlar as instancias da classe.
Para isso precisamos impedir que a classe seja instanciada, utilizando private constuctor.
o Companion Object serve para acessar metodos sem precisar instanciar a classe>
A função getInstance retorna uma intancia do repositorio, mesmo que eu chame essa função mais de 2 vez
o GuestRepository nunca vai ser instanciado mais de uma vez, isso por que sempre que já estiver inicializado
retornamos a instancia deste mesmo repo.


 */

class GuestRepository private constructor(){

    //Singleton
    companion object{

        private lateinit var repository: GuestRepository

        fun getInstance(): GuestRepository {
            if(!Companion::repository.isInitialized){
                repository = GuestRepository()
            }
            return GuestRepository()
        }
    }
}