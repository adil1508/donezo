package com.amian.donezo.viewmodels

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
	private val userRepo: UserRepository
) : ViewModel() {

	/*
	* TODO:
	*  - Add method to write to database + write to firestore
	* */

	private val currentUser by lazy {
		userRepo.observeUser()
	}

	fun addTodo(todoText: String) {
		viewModelScope.launch {
			currentUser.asLiveData().value?.let {
				todoRepo.addTodo(Todo(email = it.email, todo = todoText))
			}
		}
	}
}