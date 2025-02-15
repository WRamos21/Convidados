package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
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
    8.0 Criação da Classe de Constantes
 */

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    //Singleton
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
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
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    /* 9.0 Atualização no banco de dados
    dataBase.Update pede noma da tabela, valores (contentValues), clausa where (filtro) e os valores
    (argumentos) da where. Neste caso queremos atualizar os dados onde a seleção é a coluna ID
    e os argumentos que devem ser capturados são todos aqueles que possuem o id do nosso Guest (convidado)
     */

    fun update(guest: GuestModel): Boolean {
        try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            return true
        } catch (e: Exception) {
            return false
        }
    }


    /* 10.0 Remoção do convidado
    - DataBase.Delete utilzia o nome da tabela, a seleção where e os argumentos
     */
    fun delete(id: Int): Boolean {
        try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    /* 11.0 Listagem sem filtro
    - Query é uma instrução SQL, pedindo as instruções que devem ser seguidas
    table: String,                 Nome da tabela
    columns: Array<String>?,       Array com nome das colunas que você quer retornar (null retorna todas)
    selection: String?,            A cláusula WHERE (null retorna todos os registros)
    selectionArgs: Array<String>?, Argumentos para substituir os ? no selection
    groupBy: String?,             Cláusula GROUP BY
    having: String?,              Cláusula HAVING
    orderBy: String?              Cláusula ORDER BY
    - Como queremos todos os convidados sem filtro e sem agrupamento podemos apenas passar todas as
    colunas e o todas as configurações extra com nulas
    - Query retorna um cursor, que aponta para o começo da tabela e pode andar linha por linha, pode
    nulo caso não tenha nada na tabela
    - Recebo todos os dados que quero e crio um modelo para receber estes dados, no final preciso
    fechar o cursor
    - A função precisará retornar uma lista de GuestModel (todos os convidados do filtro)
     */
    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf("1")

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf("0")

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
}