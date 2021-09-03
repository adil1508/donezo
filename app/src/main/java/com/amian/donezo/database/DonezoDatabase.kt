package com.amian.donezo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amian.donezo.database.dao.TodoDao
import com.amian.donezo.database.dao.UserDao
import com.amian.donezo.database.entities.Todo
import com.amian.donezo.database.entities.User

@Database(entities = [User::class, Todo::class], version = 1, exportSchema = true)
abstract class DonezoDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao
	abstract fun todoDao(): TodoDao
}