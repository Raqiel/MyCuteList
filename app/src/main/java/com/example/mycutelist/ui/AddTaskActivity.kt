package com.example.mycutelist.ui


import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.todolist.datasource.TaskDataSource
import com.example.mycutelist.database.TarefasDataBase
import com.example.mycutelist.database.editar
import com.example.mycutelist.database.salvarTarefa
import com.example.mycutelist.databinding.ActivityAddTaskBinding
import com.example.mycutelist.extensions.format
import com.example.mycutelist.extensions.text
import com.example.mycutelist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private val adapter by lazy {TaskListAdapter()}
    private lateinit var dataBase : TarefasDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddTaskBinding.inflate(layoutInflater)


        if (intent.hasExtra(TASK_ID)) {
           val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.title.text = it.title
                binding.date.text = it.date
                binding.hour.text = it.hour
            }
        }
        setContentView(binding.root)

        insertListeners()

        dataBase = TarefasDataBase(this)

    }

    private fun insertListeners() {
        binding.date.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.date.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")


        }

        binding.hour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.hour.text = "$hour:$minute"

            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.cancel.setOnClickListener {
            finish()
        }

        binding.newTask.setOnClickListener {
            val task = Task(
                title = binding.title.text,
                date = binding.date.text,
                hour = binding.hour.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
           //TaskDataSource.insertTask(task)

            if (task.id > 0) {
                dataBase.editar(task)


            }else {


                val idTodo = dataBase.salvarTarefa(task)

                if (idTodo == -1L) {
                    Toast.makeText(this, "Erro ao inserir tarefa", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Tarefa inserida com sucesso", Toast.LENGTH_SHORT).show()
                }
            }

            setResult(Activity.RESULT_OK)

            finish()

        }





    }

    companion object {
        const val TASK_ID = "task_id"
    }
}



