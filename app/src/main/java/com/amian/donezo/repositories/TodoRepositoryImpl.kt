package com.amian.donezo.repositories

import com.amian.donezo.database.dao.TodoDao
import com.amian.donezo.database.entities.Todo
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDao: TodoDao) : TodoRepository {

	override suspend fun addTodo(todo: Todo) = todoDao.insertTodo(todo)
}