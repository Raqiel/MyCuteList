package com.example.mycutelist.ui


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import br.com.dio.todolist.datasource.TaskDataSource
import com.example.mycutelist.database.TarefasDataBase
import com.example.mycutelist.database.deletar
import com.example.mycutelist.database.listar
import com.example.mycutelist.databinding.ActivityMainBinding
import com.example.mycutelist.model.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val adapter by lazy {TaskListAdapter()}
    private lateinit var dataBase : TarefasDataBase

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) updateList()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBase = TarefasDataBase(this)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()



        insertListeners()

    }






        private fun insertListeners(){
            binding.fab.setOnClickListener {
                register.launch(Intent(this, AddTaskActivity::class.java))

            }

            adapter.listenerEdit = {
               val intent = Intent (this, AddTaskActivity::class.java)
                intent.putExtra(AddTaskActivity.TASK_ID, it.id)
                intent.putExtra(AddTaskActivity.TASK_ID, it.id)
                register.launch(intent)




            }
            adapter.listenerDelete = {
                dataBase.deletar(it.id)

                TaskDataSource.deleteTask(it)


                updateList()

            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
        adapter.notifyDataSetChanged()
    }

    private fun updateList() {
        val list = dataBase.listar()

        if (list.isEmpty()) {
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
            binding.rvTasks.visibility = View.GONE
        }else{
            binding.includeEmpty.emptyState.visibility = View.GONE
            binding.rvTasks.visibility = View.VISIBLE
        }




        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }



    companion object {
        private const val CREATE_NEW_TASK = 1000
    }

}

