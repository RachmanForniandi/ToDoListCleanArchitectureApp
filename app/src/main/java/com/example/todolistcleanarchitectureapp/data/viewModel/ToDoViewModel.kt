package com.example.todolistcleanarchitectureapp.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistcleanarchitectureapp.data.ToDoData
import com.example.todolistcleanarchitectureapp.data.ToDoDatabase
import com.example.todolistcleanarchitectureapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application):AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).todoDao()

    private val repository: ToDoRepository

    val getAllData:LiveData<List<ToDoData>>
    val sortingByHighPriority:LiveData<List<ToDoData>>
    val sortingByLowPriority:LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.fetchAllData
        sortingByHighPriority = repository.filterDataByHighPriority
        sortingByLowPriority = repository.filterDataByLowPriority
    }

    fun insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteData(toDoData)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }

    fun searchData(searchQuery:String):LiveData<List<ToDoData>>{
        return repository.searchDataOnDatabase(searchQuery)
    }


}