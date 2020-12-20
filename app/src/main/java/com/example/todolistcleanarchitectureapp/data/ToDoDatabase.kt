package com.example.todolistcleanarchitectureapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoData::class],version = 1,exportSchema = false)
abstract class ToDoDatabase:RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE:ToDoDatabase? = null

        fun getDatabase(context: Context):ToDoDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}