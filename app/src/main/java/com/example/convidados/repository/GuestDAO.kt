package com.example.convidados.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.convidados.model.GuestModel

/* Camada DAO e conexão de todas as partes
- Aqui eu digo que a interface é uma DAO
- Vou trazer o metodos do repository para a DAO, long permite dizer quantas inserções foram feitas,
Int quantas linhas foram criada
- Toda consulta utiliza um SQL executado no query
 */

@Dao
interface GuestDAO {

    @Insert
    fun insert(guest: GuestModel): Long

    @Update
    fun update(guest: GuestModel): Int

    @Delete
    fun delete(guest: GuestModel)

    @Query("SELECT * FROM guest WHERE id = :id")
    fun get(id: Int): GuestModel

    @Query("SELECT * FROM Guest")
    fun getAll(): List<GuestModel>

    @Query("SELECT * FROM guest WHERE presence = 1")
    fun getPresent(): List<GuestModel>

    @Query("SELECT * FROM guest WHERE presence = 0")
    fun getAbsent(): List<GuestModel>


}