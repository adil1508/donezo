package com.amian.donezo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amian.donezo.database.entities.Todo
import com.amian.donezo.repositories.TodoRepository
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val userRepository: UserRepository,
	private val todoRepository: TodoRepository
) : ViewModel() {

	val authenticated = MutableLiveData(true)

	private val todos = userRepository.currentUser.value?.let {
		it.let {
			todoRepository.observeTodos(email = it.email)
		}
	} ?: flow<List<Todo>> { listOf<Todo>() }.stateIn(GlobalScope, SharingStarted.Eagerly, listOf())

	val todosLiveData = todos.asLiveData()

	init {
		viewModelScope.launch {
			userRepository.currentUser.collect {
				if (it == null) authenticated.value = false
			}
		}
	}

}