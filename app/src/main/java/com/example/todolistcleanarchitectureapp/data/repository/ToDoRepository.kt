package com.example.todolistcleanarchitectureapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todolistcleanarchitectureapp.data.ToDoDao
import com.example.todolistcleanarchitectureapp.data.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val fetchAllData:LiveData<List<ToDoData>> = toDoDao.getAllDataTask()
    val filterDataByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val filterDataByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertDataTask(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateDataTask(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData){
        toDoDao.deleteDataTask(toDoData)
    }

    suspend fun deleteAllData(){
        toDoDao.deleteAllTask()
    }

    fun searchDataOnDatabase(searchQuery:String):LiveData<List<ToDoData>>{
        return toDoDao.searchDatabase(searchQuery)
    }


}