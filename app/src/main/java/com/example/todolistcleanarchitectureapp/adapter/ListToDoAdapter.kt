package com.example.todolistcleanarchitectureapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistcleanarchitectureapp.R
import com.example.todolistcleanarchitectureapp.data.Priority
import com.example.todolistcleanarchitectureapp.data.ToDoData
import com.example.todolistcleanarchitectureapp.fragments.ListFragmentDirections
import kotlinx.android.synthetic.main.item_to_do.view.*

class ListToDoAdapter:RecyclerView.Adapter<ListToDoAdapter.ListToDoHolder>() {

    var dataToDoList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListToDoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_to_do,parent,false)
        return ListToDoHolder(view)
    }
    override fun getItemCount(): Int {
        return dataToDoList.size
    }
    override fun onBindViewHolder(holder: ListToDoHolder, position: Int) {
        val item = dataToDoList[position]
        holder.itemView.title_txt.text = item.title
        holder.itemView.description_txt.text = item.description
        holder.itemView.row_background.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(item)
            holder.itemView.findNavController().navigate(action)
        }

        when(item.priority){
            Priority.HIGH -> holder.itemView.priority_indicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
            Priority.MEDIUM -> holder.itemView.priority_indicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.yellow))
            Priority.LOW -> holder.itemView.priority_indicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))

        }

    }
    fun setData(toData:List<ToDoData>){
        this.dataToDoList = toData
        notifyDataSetChanged()
    }


    class ListToDoHolder (itemView:View):RecyclerView.ViewHolder(itemView){

    }

}