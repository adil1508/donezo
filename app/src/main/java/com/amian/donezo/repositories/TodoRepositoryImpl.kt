package com.amian.donezo.repositories

import androidx.lifecycle.LiveData
import com.amian.donezo.database.dao.TodoDao
import com.amian.donezo.database.entities.Todo
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
	private val todoDao: TodoDao,
	firestore: FirebaseFirestore
) : TodoRepository {

	private val usersCollection = firestore.collection("users")

	override suspend fun addTodo(todo: Todo) {
		try {
			todoDao.insertTodo(todo)
		} catch (t: Throwable) {
			Timber.d(t)
		}
		addTodoToFirestore(todo)
	}

	override fun observeTodos(email: String): LiveData<List<Todo>> =
		todoDao.observeTodos(email = email)

	private fun addTodoToFirestore(todo: Todo) =
		usersCollection.whereEqualTo("email", todo.email).limit(1).get()
			.addOnCompleteListener {
				if (it.isSuccessful) {
					it.result?.documents?.first()?.let { userDoc ->
						Timber.d("Successfully got document for user: ${todo.email}")
						usersCollection.document(userDoc.id).collection("todos")
							.add(
								hashMapOf("msg" to todo.todo)
							).addOnCompleteListener { lastTask ->
								if (lastTask.isSuccessful) {
									Timber.d("Successfully wrote todo to firestore")
								} else {
									Timber.d("Failed to write todo to firestore")
								}
							}
					}
				} else {
					Timber.d("Failed to get document for user: ${todo.email}")
				}
			}


}