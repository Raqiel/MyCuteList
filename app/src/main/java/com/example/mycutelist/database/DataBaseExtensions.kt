package com.example.mycutelist.database

import android.annotation.SuppressLint
import android.content.ContentValues
import com.example.mycutelist.model.Task

fun TarefasDataBase.salvarTarefa(item: Task): Long {

    val idTarefa = writableDatabase.insert(
        "TB_TAREFAS", null, ContentValues().apply {
        put("NOME",item.title)
        put("DATA",item.date)
        put("HORA",item.hour)
    })

    return idTarefa


}

@SuppressLint("Range")
fun TarefasDataBase.listar() : List<Task> {

    val sql = "SELECT * FROM TB_TAREFAS"

    val cursor =  readableDatabase.rawQuery(sql, null)

    val taskList = mutableListOf<Task>()

    if (cursor.count > 0) {
        while (cursor.moveToNext()) {
            val tarefas = Task (
                id= cursor.getInt(cursor.getColumnIndex("ID")),
                title =  cursor.getString(cursor.getColumnIndex("NOME")),
                date = cursor.getString(cursor.getColumnIndex("DATA")),
                hour = cursor.getString(cursor.getColumnIndex("HORA"))

                    )

            taskList.add(tarefas)


        }

        cursor.close()
    }

    return  taskList
}