package com.amian.donezo.repositories

import com.amian.donezo.database.dao.TodoDao
import com.amian.donezo.database.entities.Todo
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
	private val todoDao: TodoDao,
	private val firestore: FirebaseFirestore
) : TodoRepository {

	override suspend fun addTodo(todo: Todo) {
		todoDao.insertTodo(todo)
		addToFirestore(todo)
	}

	private suspend fun addToFirestore(todo: Todo) {

		firestore.collection("users").whereEqualTo("email", todo.email).limit(1).get()
			.addOnCompleteListener {
				if (it.isSuccessful) {
					it.result?.documents?.first()?.let { userDoc ->
						Timber.d("Successfully got document for user: ${todo.email}")
						firestore.collection("users").document(userDoc.id).collection("todos").add(
							hashMapOf("msg" to todo.todo)
						).addOnCompleteListener { lastTask ->
							if (lastTask.isSuccessful) {
								Timber.d("Successfully wrote todo to firestore")
							}
						}
					}
				} else {
					Timber.d("Failed to get document for user: ${todo.email}")
				}
			}

		firestore.collection("users")
	}
}