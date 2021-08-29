package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

	private val currentUser = userRepo.observeUser()

	val authenticated = MutableLiveData(false)

	init {
		viewModelScope.launch {
			currentUser.distinctUntilChanged().collect {
				authenticated.value = it != null
			}
		}
	}

	fun login(email: String, password: String) = viewModelScope.launch {
		userRepo.login(email = email, password = password)
	}

}