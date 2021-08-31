package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
	private val userRepo: UserRepository,
) : ViewModel() {

	private val currentUser = userRepo.observeUser()

	val authenticated = MutableLiveData(false)

	val name = MutableLiveData<String?>(null)
	val email = MutableLiveData<String?>(null)
	val password = MutableLiveData<String?>(null)
	val confirmedPassword = MutableLiveData<String?>(null)

	init {
		viewModelScope.launch {
			currentUser.distinctUntilChanged().collect {
				authenticated.value = it != null
			}
		}
	}

	fun signUp(name: String, email: String, password: String) = viewModelScope.launch {
		userRepo.signup(name = name, email = email, password = password)
	}

}