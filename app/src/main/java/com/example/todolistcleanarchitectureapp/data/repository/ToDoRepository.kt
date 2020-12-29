package com.example.todolistcleanarchitectureapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todolistcleanarchitectureapp.data.ToDoDao
import com.example.todolistcleanarchitectureapp.data.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val fetchAllData:LiveData<List<ToDoData>> = toDoDao.getAllDataTask()

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
}