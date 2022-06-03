package com.example.budilnik.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budilnik.R
import com.example.budilnik.adapters.ListAdapter2
import com.example.budilnik.controller.AlarmController
import com.example.budilnik.databinding.FragmentTaskBinding
import com.example.budilnik.databinding.List2Binding
import com.example.budilnik.model.MovieViewModel
import com.example.budilnik.room.AppDatabase
import com.example.budilnik.room.ListEntity
import com.example.budilnik.room.TaskEntity
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentTaskBinding
    lateinit var listAdapter2: ListAdapter2
    lateinit var mContext: Context
    private lateinit var alarmController: AlarmController
    lateinit var vm: MovieViewModel
    private val TAG = "TaskFragment"
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(layoutInflater,container,false)
        vm = ViewModelProvider(this).get(MovieViewModel::class.java)
        appDatabase = AppDatabase.getInstance(binding.root.context)
        alarmController = AlarmController(requireContext())
        setRv()
        setUi()



        return binding.root
    }

    private fun setUi() {

        var date = ""
        var time = ""
        var isclick = false
        binding.alarm.setOnClickListener {
            if (date.isNotEmpty()) {
                var timepicker = TimePickerDialog(
                    requireContext(),
                    { view, hourOfDay, minute ->
                        val hour1 = String.format("%02d", hourOfDay)
                        val minute1 = String.format("%02d", minute)
                        time = "$hour1:$minute1"
                        binding.alarm1.visibility = View.VISIBLE
                        binding.alarmTime.visibility = View.VISIBLE
                        binding.alarmTime.text = time
                    }, 24, 60, true
                )
                timepicker.show()
            } else Toast.makeText(mContext, "please add date", Toast.LENGTH_SHORT)
                .show()

        }

        binding.calendar.setOnClickListener {

            var datatimepicker = DatePickerDialog(requireContext())
            datatimepicker.show()
            datatimepicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                val mont = String.format("%02d", month + 1)
                val day = String.format("%02d", dayOfMonth)
                date = "$day.$mont.$year"
                binding.calendar1.visibility = View.VISIBLE
                binding.calendarDate.visibility = View.VISIBLE
                binding.calendarDate.text = date
            }
        }


        binding.done.setOnClickListener {

            if (binding.taskName.text.toString().isEmpty()){
                Toast.makeText(binding.root.context, "Vazifani yozing", Toast.LENGTH_SHORT).show()
            }else if (date.isEmpty()){
                Toast.makeText(binding.root.context, "Sanani kiriting", Toast.LENGTH_SHORT).show()
            }else if (time.isEmpty()){
                Toast.makeText(binding.root.context, "Vaqtni belgilang", Toast.LENGTH_SHORT).show()
            }else{
                alarmController.setAlarm(
                    "${time.substring(0, 2)}${time.substring(3)}".toInt(), date, time,
                    binding.taskName.text.toString()
                )

                val taskEntity = TaskEntity()
                taskEntity.task = binding.taskName.text.toString()
                taskEntity.calendar = date
                taskEntity.list_name = binding.categoryName.getText().toString()
                taskEntity.taskId = "${time.substring(0, 2)}${time.substring(3)}".toInt()
                taskEntity.time = time

                appDatabase.taskDao().addTask(taskEntity)
                Toast.makeText(binding.root.context, "Muvaffaqiyatli sozlandi", Toast.LENGTH_SHORT).show()

            }




        }

        binding.cancel.setOnClickListener {
            if (time.isNotEmpty()){
                alarmController.disableAlarm("${time.substring(0, 2)}${time.substring(3)}".toInt())
                Toast.makeText(binding.root.context, "alarm is canceled", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(binding.root.context, "Vaqt sozlanmagan", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun setRv() {
        vm.allMovies.observe(this, Observer { items ->

            listAdapter2 = ListAdapter2(items,object : ListAdapter2.OnItemClickListener{
                override fun onItemClick(
                    malumotlar: ListEntity,
                    malumotItemBinding: List2Binding,
                    position: Int
                ) {
                    Toast.makeText(binding.root.context, "${malumotlar.title}", Toast.LENGTH_SHORT)
                        .show()
                    binding.categoryName.text = malumotlar.title
                }


            })
            binding.categoryRv.adapter = listAdapter2


        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}