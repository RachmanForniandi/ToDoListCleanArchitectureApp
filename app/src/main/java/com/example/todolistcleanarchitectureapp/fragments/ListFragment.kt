package com.example.todolistcleanarchitectureapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.adapter.ListToDoAdapter
import com.example.todolistcleanarchitectureapp.data.ToDoData
import com.example.todolistcleanarchitectureapp.data.viewModel.SharedViewModel
import com.example.todolistcleanarchitectureapp.data.viewModel.ToDoViewModel
import com.example.todolistcleanarchitectureapp.databinding.FragmentListBinding
import com.example.todolistcleanarchitectureapp.utility.SwipeToDelete
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private val toDoViewModel:ToDoViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by viewModels()

    private var fragmentListBinding:FragmentListBinding? = null
    private val binding get() = fragmentListBinding

    private val listToDoAdapter:ListToDoAdapter by lazy { ListToDoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentListBinding = FragmentListBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.sharedViewModel = sharedViewModel
        displayDataRecyclerView()



        toDoViewModel.getAllData.observe(viewLifecycleOwner, {
            data ->
            sharedViewModel.checkIfDatabaseEmpty(data)
            listToDoAdapter.setData(data)
        })
        /*sharedViewModel.emptyDb.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })*/
        /*view.fab_add_data.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }*/

        /*view.layout_list_todo.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }*/

        setHasOptionsMenu(true)
        return binding?.root

    }

    private fun displayDataRecyclerView() {
        val rvToDo = binding?.listTodoData
        //rvToDo.layoutManager = LinearLayoutManager(requireActivity())
        rvToDo?.adapter = listToDoAdapter

        rvToDo?.let { swipeToDelete(it) }
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object :SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem =listToDoAdapter.dataToDoList[viewHolder.adapterPosition]
                //delete item
                toDoViewModel.deleteData(deletedItem)
                //restore deleted item
                restoreDeletedData(viewHolder.itemView,deletedItem,viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    private fun restoreDeletedData(view:View, deletedItem: ToDoData,position: Int){
        val snackBar = Snackbar.make(view,"Deleted '${deletedItem.title}'",Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo"){
            toDoViewModel.insertData(deletedItem)
            listToDoAdapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all){
            confirmClearAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmClearAllData() {
        //Toast.makeText(requireContext(),"test Delete", Toast.LENGTH_SHORT).show()

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

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentListBinding = null
    }

}