package com.example.mycutelist.ui


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import br.com.dio.todolist.datasource.TaskDataSource
import com.example.mycutelist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val adapter by lazy {TaskListAdapter()}

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) updateList()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                adapter.notifyDataSetChanged()



            }
            adapter.listenerDelete = {
                TaskDataSource.deleteTask(it)
                updateList()

            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
        val list = TaskDataSource.getList()

        if (list.isEmpty()) {
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
            binding.rvTasks.visibility = View.GONE
        }else{
            binding.includeEmpty.emptyState.visibility = View.GONE
            binding.rvTasks.visibility = View.VISIBLE
        }




        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }

}