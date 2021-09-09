package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

	val emailError = MutableLiveData<String?>(null)
	val passwordError = MutableLiveData<String?>(null)

	val email = MutableLiveData("")
	val password = MutableLiveData("")

	fun login() {

		validateEmail()
		validatePassword()

		if (emailError.value == null && passwordError.value == null) viewModelScope.launch {
			userRepo.login(email = email.value!!, password = password.value!!)
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

	private fun validatePassword() {
		if (password.value.isNullOrBlank()) {
			passwordError.value = "Password is required"
			return
		}

		passwordError.value = null
	}


}