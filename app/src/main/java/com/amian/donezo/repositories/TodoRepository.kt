package com.amian.donezo.repositories

import com.amian.donezo.database.entities.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

	suspend fun addTodo(todo: Todo)

	fun observeTodos(email: String): Flow<List<Todo>>

}