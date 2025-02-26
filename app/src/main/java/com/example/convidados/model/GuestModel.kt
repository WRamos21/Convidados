package com.example.convidados.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/* Implementando o ROOM
Utilizamos @ para implementar um anotation para colocar atributos e configurações a mais na classe
Basicamnete colocando Entity digo que a guestModel é um entidade, siginifica que ela será salva no
banco de dados, de forma que o ROOM vai ser que precisa persisitir GuestModel no banco
- Desta forma não precisamos mais se preocupar com a criação da tabela lá me GuestDataBase
- Preciso criar a anotation para marcar para o ROOM como colunas usando @ColumnInfo e @PrymaryKey
para identificar uma coluna como chave primaria

 */

@Entity(tableName = "Guest")
class GuestModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "presence")
    var presence: Boolean = false

}