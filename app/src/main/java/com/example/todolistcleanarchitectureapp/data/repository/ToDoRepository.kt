package com.example.todolistcleanarchitectureapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todolistcleanarchitectureapp.data.ToDoDao
import com.example.todolistcleanarchitectureapp.data.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val fetchAllData:LiveData<List<ToDoData>> = toDoDao.getAllDataTask()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertDataTask(toDoData)
    }
}