package com.amian.donezo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amian.donezo.database.entities.Todo
import com.amian.donezo.repositories.TodoRepository
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
	private val todoRepo: TodoRepository,
	private val userRepo: UserRepository
) : ViewModel() {

	val todoText = MutableLiveData("")

	fun addTodo() = userRepo.currentUser.value?.let { user ->
		todoText.value.takeIf { !it.isNullOrBlank() }?.let { todoTextVal ->
			GlobalScope.launch(Dispatchers.IO) {
				todoRepo.addTodo(Todo(email = user.email, todo = todoTextVal, done = false))
			}
			// reset it's value
			todoText.value = ""
		}
	}

}