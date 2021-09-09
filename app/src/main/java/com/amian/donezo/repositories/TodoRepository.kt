package com.amian.donezo.repositories

import androidx.lifecycle.LiveData
import com.amian.donezo.database.entities.Todo

interface TodoRepository {

	suspend fun addTodo(todo: Todo)

	fun observeTodos(email: String): LiveData<List<Todo>>

}