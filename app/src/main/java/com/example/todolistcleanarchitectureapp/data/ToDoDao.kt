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
    suspend fun updateDataTask(toDoData: ToDoData)

    @Delete
    suspend fun deleteDataTask(toDoData: ToDoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery:String):LiveData<List<ToDoData>>
}