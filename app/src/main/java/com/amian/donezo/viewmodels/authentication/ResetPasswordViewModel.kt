package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) :
	ViewModel() {

	val emailError = MutableLiveData<String?>(null)
	val email = MutableLiveData("")

	val resetEmailSent = MutableLiveData(false)

	fun resetPassword() {

		validateEmail()

		if (emailError.value == null)
			firebaseAuth.sendPasswordResetEmail(email.value!!)
				.addOnCompleteListener { resetEmailSent.value = it.isSuccessful }
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

}