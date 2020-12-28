package com.example.todolistcleanarchitectureapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ")
    fun getAllDataTask():LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDataTask(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)
}