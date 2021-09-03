package com.amian.donezo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amian.donezo.database.entities.Todo

@Dao
interface TodoDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTodo(todo: Todo)

	@Delete
	suspend fun deleteTodo(todo: Todo)

	@Query("SELECT * FROM ${Todo.TODO_TABLE} WHERE email = :email")
	suspend fun getTodos(email: String)
}