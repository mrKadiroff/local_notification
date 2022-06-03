package com.example.budilnik.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.budilnik.R
import com.example.budilnik.adapters.ListAdapter
import com.example.budilnik.databinding.CustomviewLayoutBinding
import com.example.budilnik.databinding.FragmentHomeBinding
import com.example.budilnik.model.MovieViewModel
import com.example.budilnik.room.AppDatabase
import com.example.budilnik.room.ListEntity
import java.lang.reflect.Method

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

    lateinit var binding: FragmentHomeBinding
    lateinit var appDatabase: AppDatabase
    lateinit var listAdapter: ListAdapter
    lateinit var malumList:ArrayList<ListEntity>
    private val TAG = "HomeFragment"
    lateinit var vm: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        vm = ViewModelProvider(this).get(MovieViewModel::class.java)
        appDatabase = AppDatabase.getInstance(binding.root.context)




        insertToRoom()
        newList()






        return binding.root
    }

    private fun newList() {
        binding.floatingActionButton.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(binding.root.context,binding.floatingActionButton)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->

                if (item.itemId == R.id.list){
                    val alertDialog = AlertDialog.Builder(binding.root.context)
                    val dialog = alertDialog.create()
                    val dialogView = CustomviewLayoutBinding.inflate(
                        LayoutInflater.from(binding.root.context),null,false
                    )

                    dialogView.apply {
                        qora.setOnClickListener {
                            containtere.setText("#252A31")
                            Toast.makeText(binding.root.context, "Qora rang tanlandi", Toast.LENGTH_SHORT).show()
                        }
                        blue.setOnClickListener {
                            containtere.setText("#006CFF")
                            Toast.makeText(binding.root.context, "Ko'k rang tanlandi", Toast.LENGTH_SHORT).show()
                        }
                        fioletuvy.setOnClickListener {
                            containtere.setText("#B678FF")
                            Toast.makeText(binding.root.context, "Fialetoviy rang tanlandi", Toast.LENGTH_SHORT).show()
                        }
                        sariq.setOnClickListener {
                            containtere.setText("#FFE761")
                            Toast.makeText(binding.root.context, "Sariq rang tanlandi", Toast.LENGTH_SHORT).show()
                        }
                        red.setOnClickListener {
                            containtere.setText("#F45E6D")
                            Toast.makeText(binding.root.context, "Qizil rang tanlandi", Toast.LENGTH_SHORT).show()
                        }
                        yashil.setOnClickListener {
                            containtere.setText("#61DEA4")
                            Toast.makeText(binding.root.context, "Yashil rang tanlandi", Toast.LENGTH_SHORT).show()
                        }
                    }


                    dialogView.addd.setOnClickListener {
                        val categoriya = dialogView.category.text.toString()
                        val color = dialogView.containtere.text.toString()
                        if (categoriya.isEmpty()){
                            Toast.makeText(binding.root.context, "Kategoriya nomini kiriting", Toast.LENGTH_SHORT).show()
                        }else if (color.isEmpty()){
                            Toast.makeText(binding.root.context, "Rang kiriting", Toast.LENGTH_SHORT).show()
                        }else{
                            appDatabase.listDao().insert2(ListEntity(categoriya,color,"#FFFFFF",1))


                            dialog.dismiss()
                        }

                    }



                    dialog.setView(dialogView.root)
                    dialog.show()
                }

                if (item.itemId == R.id.task){
                    findNavController().navigate(R.id.taskFragment)
                }




                true
            })

            // show icons on popup menu
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
            }else{
                try {
                    val fields = popupMenu.javaClass.declaredFields
                    for (field in fields) {
                        if ("mPopup" == field.name) {
                            field.isAccessible = true
                            val menuPopupHelper = field[popupMenu]
                            val classPopupHelper =
                                Class.forName(menuPopupHelper.javaClass.name)
                            val setForceIcons: Method = classPopupHelper.getMethod(
                                "setForceShowIcon",
                                Boolean::class.javaPrimitiveType
                            )
                            setForceIcons.invoke(menuPopupHelper, true)
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            popupMenu.show()

        }
    }

    private fun insertToRoom() {
        vm.allMovies.observe(this, Observer { items ->
            if (items.isEmpty()) {
                vm.insert(ListEntity("Inbox","#EBEFF5","#252A31",1))
                vm.insert(ListEntity("Work","#61DEA4","#FFFFFF",1))
                vm.insert(ListEntity("Shopping","#F45E6D","#FFFFFF",1))
                vm.insert(ListEntity("Family","#FFE761","#252A31",1))
                vm.insert(ListEntity("Personal","#B678FF","#FFFFFF",1))
            }

            Log.d(TAG, "ITEMS: $items")
            listAdapter = ListAdapter(items,object :ListAdapter.OnItemClickListener{
                override fun onItemClick(malumotlar: ListEntity) {
                    var bundle = Bundle()
                    bundle.putSerializable("key",malumotlar)
                    findNavController().navigate(R.id.childFragment,bundle)
                }

            })

            binding.listRv.adapter = listAdapter
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}