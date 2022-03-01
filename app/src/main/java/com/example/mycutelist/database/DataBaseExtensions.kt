package com.example.mycutelist.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.LocusId
import androidx.core.content.contentValuesOf
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


fun TarefasDataBase.deletar(id: Int) {
    writableDatabase.delete("TB_TAREFAS", "ID=?",Array<String>(1) {"$id"})

}

fun TarefasDataBase.editar(task: Task) {
    val id = task.id
    val values = ContentValues ()
    values.put("NOME",task.title)
    values.put("DATA",task.date)
    values.put("HORA",task.hour)
    writableDatabase.update("TB_TAREFAS", values, "ID=?" , Array<String>(1) {"$id"})
}

