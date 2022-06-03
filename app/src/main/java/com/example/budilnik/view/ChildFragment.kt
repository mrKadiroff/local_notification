package com.example.budilnik.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budilnik.adapters.TaskAdapter
import com.example.budilnik.databinding.FragmentChildBinding
import com.example.budilnik.room.AppDatabase
import com.example.budilnik.room.ListEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChildFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildFragment : Fragment() {
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

    lateinit var binding: FragmentChildBinding
    private val TAG = "ChildFragment"
    lateinit var appDatabase: AppDatabase
    lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChildBinding.inflate(layoutInflater,container,false)
        appDatabase = AppDatabase.getInstance(binding.root.context)

        setUi()


        return binding.root
    }

    private fun setUi() {
        val listData = arguments?.getSerializable("key") as ListEntity
        binding.name.text = listData.title
        binding.totall.text = listData.total.toString()
        binding.constarianla.setBackgroundColor(Color.parseColor(listData.color))
        binding.name.setTextColor(Color.parseColor(listData.textcolor))
        binding.totall.setTextColor(Color.parseColor(listData.textcolor))

        val taskByListName = appDatabase.taskDao().getTaskByListName(listData.title)
        taskAdapter = TaskAdapter(taskByListName,listData.color,listData.textcolor)
        binding.listRv.adapter = taskAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChildFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChildFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}