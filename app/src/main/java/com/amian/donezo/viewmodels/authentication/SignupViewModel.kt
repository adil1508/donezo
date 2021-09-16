package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
	private val userRepo: UserRepository,
) : ViewModel() {

	val authenticated = MutableLiveData(false)

	val name = MutableLiveData("")
	val nameError = MutableLiveData<String?>(null)

	val email = MutableLiveData("")
	val emailError = MutableLiveData<String?>(null)

	val password = MutableLiveData("")
	val passwordError = MutableLiveData<String?>(null)

	val confirmedPassword = MutableLiveData("")
	val confirmedPasswordError = MutableLiveData<String?>(null)

	init {
		viewModelScope.launch {
			userRepo.currentUser.collect {
				if (it != null) authenticated.value = true
			}
		}
	}

	fun signUp() {

		validateEmail()
		validateName()
		validatePasswords()

		if (nameError.value == null && emailError.value == null && passwordError.value == null && confirmedPasswordError.value == null)
			viewModelScope.launch {
				userRepo.signup(
					name = name.value!!,
					email = email.value!!,
					password = password.value!!
				)
			}
	}

	private fun validateEmail() {
		if (email.value.isNullOrBlank()) {
			emailError.value = "Email is required"
			return
		}
		if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value as CharSequence).matches()) {
			emailError.value = "Email is not valid"
			return
		}

		emailError.value = null
	}

	private fun validateName() {
		if (name.value.isNullOrBlank()) {
			nameError.value = "Name is required"
			return
		}
		if (name.value!!.length > 120) {
			nameError.value = "Name must be less than 120 characters"
			return
		}

		nameError.value = null
	}

	private fun validatePasswords() {

		if (password.value != confirmedPassword.value) {
			passwordError.value = "Passwords do not match"
			confirmedPasswordError.value = "Passwords do not match"
			return
		}

		if (password.value.isNullOrBlank()) {
			passwordError.value = "Password is required"
			return
		}

		if (confirmedPassword.value.isNullOrBlank()) {
			confirmedPasswordError.value = "Please re-enter password"
			return
		}

		passwordError.value = null
		confirmedPasswordError.value = null
	}

}