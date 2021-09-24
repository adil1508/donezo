package com.amian.donezo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amian.donezo.database.entities.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM ${Todo.TODO_TABLE} WHERE email = :email ORDER BY id desc")
    fun observeTodos(email: String): Flow<List<Todo>>

    @Query("DELETE FROM ${Todo.TODO_TABLE}")
    fun deleteAllTodos()
}