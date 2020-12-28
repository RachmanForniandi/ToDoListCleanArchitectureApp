package com.example.todolistcleanarchitectureapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.adapter.ListToDoAdapter
import com.example.todolistcleanarchitectureapp.data.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val toDoViewModel:ToDoViewModel by viewModels()
    private val listToDoAdapter:ListToDoAdapter by lazy { ListToDoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_list, container, false)

        val rvToDo = view.list_todo_data
        rvToDo.adapter = listToDoAdapter
        //rvToDo.layoutManager = LinearLayoutManager(requireActivity())

        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            data ->
            listToDoAdapter.setData(data)
        })
        view.fab_add_data.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        /*view.layout_list_todo.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }*/

        setHasOptionsMenu(true)
        return view

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }

}