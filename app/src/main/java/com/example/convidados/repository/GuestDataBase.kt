package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

/* 5.0 Criando o banco de dados
- A primeira coisa a ser feita é implementar a classe SQLiteOpenHelper, mas o kotlin vai pedir para
add parametros no construtor, entre eles factory (o banco de dados retorna um cursor para o dado acessado,
e usar o factory permite modificar algumas funcionalidades, mas não será usado neste projeto),
version (versão do banco)
- No lugar de passar estes valores sempre que intancamos o banco, podemos criar um companion object,
para armazenar o nome e versão do dataBase
- Implementamos os metodos do contrato: onCreate e onUpgrade
- onCreate só é chamado quando o banco não foi criado ainda
posso criar o banco de dados com:
        db.execSQL(
            "CREATE TABLE Guest(" +
                    "id integer primary key autoincrement," +
                    "name text, " +
                    "presence integer);")
Primary key diz que a ID é um identificador unico, nunca pode ter alguma ID igual, autoincremento
diz que o banco é que vai criar os IDs unicos

 */
//class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

/*override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(
        "CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + " (" +
                DataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, " +
                DataBaseConstants.GUEST.COLUMNS.NAME + " text, " +
                DataBaseConstants.GUEST.COLUMNS.PRESENCE + " integer);"
    )
}

override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    TODO("Not yet implemented")
}

companion object {
    private const val NAME = "guestdb"
    private const val VERSION = 1
}



}*/


/* Camada de banco de dados
- Vou substituir o GuestDataBase por uma nova versão que utiliza RoomDataBase, mas o room precisa que
a classe seja abstrata
- databaseBuilder é quem contruoi o banco de dados, pede o contexto, a classe do tipo do banco
- Vamo transferir o singleton do repository para o DataBase
- synchronized impede que duas threads executem o mesmo codigo
- Migration serve para atualizar o banco de dados
- AllowMainTheredQueries permite ser carregado na thread main, informando que é leve
 */

@Database(entities = [GuestModel::class], version = 1)
abstract class GuestDataBase() : RoomDatabase() {

    abstract fun guestDAO(): GuestDAO

    companion object {
        private lateinit var INTANCE: GuestDataBase
        fun getDataBase(context: Context): GuestDataBase {
            if (!::INTANCE.isInitialized) {
                synchronized(GuestDataBase::class) {
                    INTANCE = Room.databaseBuilder(context, GuestDataBase::class.java, "guestdb")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INTANCE
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2){
            override fun migrate(db: SupportSQLiteDatabase) {
                TODO("Not yet implemented")
            }

        }

    }
}