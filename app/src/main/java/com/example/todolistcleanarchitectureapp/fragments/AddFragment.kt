package com.example.todolistcleanarchitectureapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.data.ToDoData
import com.example.todolistcleanarchitectureapp.data.viewModel.SharedViewModel
import com.example.todolistcleanarchitectureapp.data.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val todoViewModel:ToDoViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        view.sp_priority.onItemSelectedListener = sharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add){
            insertToLocalDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertToLocalDb() {
        val inputTitle = et_title.text.toString()
        val inputPriority = sp_priority.selectedItem.toString()
        val inputDescription = et_description_multiline.text.toString()

        val validationInputData = sharedViewModel.verifyInputFromUser(inputTitle,inputDescription)

        if (validationInputData){
            val newData = ToDoData(0,
                    inputTitle,sharedViewModel.parsePriority(inputPriority),
                    inputDescription)
            todoViewModel.insertData(newData)
            Toast.makeText(context,"Successfully  added.",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(context,"Please fill it out all fields.",Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun verifyInputFromUser(title: String,description:String):Boolean{
        return if (TextUtils.isEmpty(title)||TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty()|| description.isEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when(priority){
            "High Priority"->{Priority.HIGH}
            "Medium Priority"->{Priority.MEDIUM}
            "Low Priority"->{Priority.LOW}
            else ->Priority.LOW
        }
    }*/



}