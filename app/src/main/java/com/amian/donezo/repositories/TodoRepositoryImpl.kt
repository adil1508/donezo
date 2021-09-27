package com.amian.donezo.repositories

import com.amian.donezo.Constants.Companion.FIRESTORE_TODOS_MSG_KEY
import com.amian.donezo.Constants.Companion.FIRESTORE_USERS_EMAIL_KEY
import com.amian.donezo.Constants.Companion.FIRESTORE_USERS_TABLE
import com.amian.donezo.Constants.Companion.FIRESTORE_USERS_TODOS_TABLE
import com.amian.donezo.database.dao.TodoDao
import com.amian.donezo.database.entities.Todo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    firestore: FirebaseFirestore
) : TodoRepository {

    private val usersCollection = firestore.collection(FIRESTORE_USERS_TABLE)

    override suspend fun addTodo(todo: Todo) {
        try {
            todoDao.insertTodo(todo)
        } catch (t: Throwable) {
            Timber.e(t)
        }
        addTodoToFirestore(todo)
    }

    override fun observeTodos(email: String): StateFlow<List<Todo>> =
        todoDao.observeTodos(email = email).stateIn(GlobalScope, SharingStarted.Eagerly, listOf())

    override fun refreshTodos(email: String) {
        usersCollection.whereEqualTo(FIRESTORE_USERS_EMAIL_KEY, email).limit(1).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.documents?.first()?.let { userDoc ->
                        usersCollection.document(userDoc.id).collection(FIRESTORE_USERS_TODOS_TABLE)
                            .get()
                            .addOnCompleteListener { todoTask ->
                                if (todoTask.isSuccessful) {
                                    Timber.d("Got ${todoTask.result?.documents?.size} todos for $email")
                                    GlobalScope.launch(Dispatchers.IO) {
                                        todoTask.result?.documents?.forEach { remoteTodoDoc ->
                                            remoteTodoDoc.getString(FIRESTORE_TODOS_MSG_KEY)
                                                ?.let { remoteTodo ->
                                                    todoDao.insertTodo(
                                                        Todo(
                                                            email = email,
                                                            todo = remoteTodo
                                                        )
                                                    )
                                                }
                                        }
                                    }
                                }
                            }
                    }
                } else {
                    Timber.d("Could not fetch latest todos for $email")
                }
            }
    }

    override fun deleteAllTodos() = todoDao.deleteAllTodos()

    private fun addTodoToFirestore(todo: Todo) =
        usersCollection.whereEqualTo(FIRESTORE_USERS_EMAIL_KEY, todo.email).limit(1).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.documents?.first()?.let { userDoc ->
                        Timber.d("Successfully got document for user: ${todo.email}")
                        usersCollection.document(userDoc.id).collection("todos")
                            .add(
                                hashMapOf(FIRESTORE_TODOS_MSG_KEY to todo.todo)
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