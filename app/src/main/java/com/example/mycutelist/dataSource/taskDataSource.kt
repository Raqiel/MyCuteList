package com.example.mycutelist.dataSource

import com.example.mycutelist.model.Task

object TaskDataSource{
    private val  list = arrayListOf<Task>()

    fun getlist() =  list.toList()

    fun insertTask(task: Task){
        list.add(task.copy(id = list.size + 1))
    }
}
