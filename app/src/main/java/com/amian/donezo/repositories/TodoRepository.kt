package com.amian.donezo.repositories

import com.amian.donezo.database.entities.Todo
import kotlinx.coroutines.flow.StateFlow

interface TodoRepository {

	suspend fun addTodo(todo: Todo)

	fun observeTodos(email: String): StateFlow<List<Todo>>

	fun refreshTodos(email: String)

	fun deleteAllTodos()

}