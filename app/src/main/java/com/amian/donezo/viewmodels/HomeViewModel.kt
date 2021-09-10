package com.amian.donezo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.TodoRepository
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val userRepository: UserRepository,
	private val todoRepository: TodoRepository
) : ViewModel() {

	val authenticated = MutableLiveData(true)

	val todos = userRepository.currentUser.value.let {
		it.let {
			todoRepository.observeTodos(email = it?.email ?: "")
		}
	}

	init {
		viewModelScope.launch {
			userRepository.currentUser.collect {
				if (it == null) authenticated.value = false
			}
		}
	}

}