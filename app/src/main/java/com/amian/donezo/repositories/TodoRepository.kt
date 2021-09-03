package com.amian.donezo.repositories

import com.amian.donezo.database.entities.Todo

interface TodoRepository {

	suspend fun addTodo(todo: Todo)

}