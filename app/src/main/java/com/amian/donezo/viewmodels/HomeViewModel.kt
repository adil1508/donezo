package com.amian.donezo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.TodoRepository
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
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
    }

    val todosLiveData = todos?.asLiveData()

    init {
        viewModelScope.launch {
            userRepository.currentUser.collectLatest {
                if (it == null) authenticated.value = false
            }
        }
        refreshTodos()
    }

    private fun refreshTodos() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.currentUser.value?.let {
            todoRepository.refreshTodos(it.email)
            Timber.d("Collected non-null user in HomeViewModel")
        }
    }
}