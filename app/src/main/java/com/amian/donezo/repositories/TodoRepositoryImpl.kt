package com.amian.donezo.repositories

import com.amian.donezo.database.dao.TodoDao
import com.amian.donezo.database.entities.Todo
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDao: TodoDao) : TodoRepository {

	// TODO: this method also needs to write this stuff to Firestore
	override suspend fun addTodo(todo: Todo) = todoDao.insertTodo(todo)
}