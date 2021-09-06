package com.amian.donezo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amian.donezo.database.entities.Todo
import com.amian.donezo.repositories.TodoRepository
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
	private val todoRepo: TodoRepository,
	userRepo: UserRepository
) : ViewModel() {

	val todoText = MutableLiveData("")

	val currentUserLiveData = userRepo.observeUser().asLiveData()

	fun addTodo() = viewModelScope.launch {
		currentUserLiveData.value?.let { user ->
			todoText.value.takeIf { !it.isNullOrBlank() }.let { todoTextVal ->
				todoRepo.addTodo(Todo(email = user.email, todo = todoTextVal!!))
				// reset it's value
				todoText.value = ""
			}
		}
	}

}