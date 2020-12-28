package com.example.todolistcleanarchitectureapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.data.Priority
import com.example.todolistcleanarchitectureapp.data.ToDoData
import com.example.todolistcleanarchitectureapp.data.viewModel.SharedViewModel
import com.example.todolistcleanarchitectureapp.data.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel:SharedViewModel by viewModels()
    private val mTodoViewModel:ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        view.update_et_title.setText(args.currentItem.title)
        view.update_et_description_multiline.setText(args.currentItem.description)
        view.update_sp_priority.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.update_sp_priority.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId== R.id.menu_save){
            updateDataItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDataItem() {
        val updateTitle = update_et_title.text.toString()
        val updatePriority = update_sp_priority.selectedItem.toString()
        val updateDescription = update_et_description_multiline.text.toString()

        val validation = mSharedViewModel.verifyInputFromUser(updateTitle,updateDescription)

        if (validation){
            //update current item
            val updateItem = ToDoData(args.currentItem.id,
                    updateTitle,mSharedViewModel.parsePriority(updatePriority),
                    updateDescription)
            mTodoViewModel.updateData(updateItem)
            Toast.makeText(context,"Successfully  updated.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(context,"Please fill it out all fields.", Toast.LENGTH_SHORT).show()
        }
    }


}