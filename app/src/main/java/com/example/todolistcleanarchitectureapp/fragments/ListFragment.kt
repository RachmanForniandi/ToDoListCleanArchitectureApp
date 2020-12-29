package com.example.todolistcleanarchitectureapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.adapter.ListToDoAdapter
import com.example.todolistcleanarchitectureapp.data.viewModel.SharedViewModel
import com.example.todolistcleanarchitectureapp.data.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val toDoViewModel:ToDoViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by viewModels()

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
            sharedViewModel.checkIfDatabaseEmpty(data)
            listToDoAdapter.setData(data)
        })

        sharedViewModel.emptyDb.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
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

    private fun showEmptyDatabaseViews(emptyData:Boolean) {
        if (emptyData){
            view?.img_view_no_data?.visibility = View.VISIBLE
            view?.tv_no_data?.visibility = View.VISIBLE
        }else{
            view?.img_view_no_data?.visibility = View.INVISIBLE
            view?.tv_no_data?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all){
            confirmClearAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmClearAllData() {
        Toast.makeText(requireContext(),"test Delete", Toast.LENGTH_SHORT).show()

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.deleteAllData()
            Toast.makeText(
                    requireContext(),
                    "Successfully Delete All Data",
                    Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything ?")
        builder.setMessage("Are you sure you want to delete all data ?")
        builder.create().show()
    }

}