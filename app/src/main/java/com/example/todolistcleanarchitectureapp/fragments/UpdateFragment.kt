package com.example.todolistcleanarchitectureapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.data.ToDoData
import com.example.todolistcleanarchitectureapp.data.viewModel.SharedViewModel
import com.example.todolistcleanarchitectureapp.data.viewModel.ToDoViewModel
import com.example.todolistcleanarchitectureapp.databinding.FragmentUpdateBinding



class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel:SharedViewModel by viewModels()
    private val mTodoViewModel:ToDoViewModel by viewModels()

    private var fragmentUpdateBinding:FragmentUpdateBinding?= null
    private val binding get() = fragmentUpdateBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentUpdateBinding = FragmentUpdateBinding.inflate(inflater, container, false)
        //val view =inflater.inflate(R.layout.fragment_update, container, false)
        binding.argsUpdate = args
        setHasOptionsMenu(true)

       /* fragmentUpdateBinding.updateEtTitle.setText(args.currentItem.title)
        view.update_et_description_multiline.setText(args.currentItem.description)
        view.update_sp_priority.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))*/
        binding.updateSpPriority.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
                R.id.menu_save-> updateDataItem()
                R.id.menu_delete-> deleteDataItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDataItem() {
        val updateTitle = binding.updateEtTitle.text.toString()
        val updatePriority = binding.updateSpPriority.selectedItem.toString()
        val updateDescription = binding.updateEtDescriptionMultiline.text.toString()

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

    private fun deleteDataItem() {
        Toast.makeText(requireContext(),"test Delete",Toast.LENGTH_SHORT).show()

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTodoViewModel.deleteData(args.currentItem)
            Toast.makeText(
                    requireContext(),
                    "Successfully Deleted: ${args.currentItem.title}",
                    Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentUpdateBinding = null
    }
}