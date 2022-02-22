package com.example.mycutelist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TarefasDataBase(
    context: Context
):SQLiteOpenHelper(context, "tarefas.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val sql = """
            CREATE TABLE TB_TAREFAS (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            NOME TEXT,
            DATA TEXT,
            HORA TEXT
            );
            """".trimIndent()

        db?.execSQL (sql)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS TB_TAREFAS ")
        onCreate(db)

    }

}