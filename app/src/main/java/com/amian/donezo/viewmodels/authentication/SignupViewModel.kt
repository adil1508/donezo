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
	val nameError = MutableLiveData<String?>(null)

	val email = MutableLiveData<String?>(null)
	val emailError = MutableLiveData<String?>(null)

	val password = MutableLiveData<String?>(null)
	val passwordError = MutableLiveData<String?>(null)

	val confirmedPassword = MutableLiveData<String?>(null)
	val confirmedPasswordError = MutableLiveData<String?>(null)


	init {
		viewModelScope.launch {
			currentUser.distinctUntilChanged().collect {
				authenticated.value = it != null
			}
		}
	}

	fun signUp() {

		validateEmail(email = email.value)
		validateName(name.value)
		validatePasswords(password.value, confirmedPassword.value)

		if (nameError.value == null && emailError.value == null && passwordError.value == null && confirmedPasswordError.value == null)
			viewModelScope.launch {
				userRepo.signup(
					name = name.value!!,
					email = email.value!!,
					password = password.value!!
				)
			}
	}

	private fun validateEmail(email: String?) {
		if (email.isNullOrBlank()) {
			emailError.value = "Email is required"
			return
		}
		if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			emailError.value = "Email is not valid"
			return
		}

		emailError.value = null
	}

	private fun validateName(name: String?) {
		if (name.isNullOrBlank()) {
			nameError.value = "Name is required"
			return
		}
		if (name.length > 120) {
			nameError.value = "Name must be less than 120 characters"
			return
		}

		nameError.value = null
	}

	private fun validatePasswords(password: String?, confirmedPassword: String?) {

		if (password != confirmedPassword) {
			passwordError.value = "Passwords do not match"
			confirmedPasswordError.value = "Passwords do not match"
			return
		}

		if (password.isNullOrBlank()) {
			passwordError.value = "Password is required"
			return
		}

		if (confirmedPassword.isNullOrBlank()) {
			confirmedPasswordError.value = "Please re-enter password"
			return
		}

		passwordError.value = null
		confirmedPasswordError.value = null
	}

}