package com.example.todolistcleanarchitectureapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.data.Priority
import com.example.todolistcleanarchitectureapp.data.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        view.update_et_title.setText(args.currentItem.title)
        view.update_et_description_multiline.setText(args.currentItem.description)
        view.update_sp_priority.setSelection(parsePriority(args.currentItem.priority))
        view.update_sp_priority.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    private fun parsePriority(priority: Priority):Int{
        return when(priority){
            Priority.HIGH->0
            Priority.MEDIUM->1
            Priority.LOW->2
        }
    }
}