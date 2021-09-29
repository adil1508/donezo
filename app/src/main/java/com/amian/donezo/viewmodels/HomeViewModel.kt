package com.amian.donezo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.TodoRepository
import com.amian.donezo.repositories.UserRepository
import com.amian.donezo.views.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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

    private val todos = userRepository.currentUser.flatMapLatest {
        it?.let {
            todoRepository.observeTodos(email = it.email)
        } ?: flowOf(listOf())
    }

    private val listItems = flow {
        todos.collect {
            emit(
                if (it.isNullOrEmpty()) listOf(HomeFragment.ListItem.EmptyListItem())
                else it.map { item -> HomeFragment.ListItem.TodoListItem(item) }
            )
        }
    }

    val listItemsLiveData = listItems.asLiveData()

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