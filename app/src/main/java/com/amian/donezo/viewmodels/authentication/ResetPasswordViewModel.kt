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
	val email = MutableLiveData<String?>(null)

	val resetEmailSent = MutableLiveData(false)

	fun resetPassword() {

		validateEmail(email = email.value)

		if (emailError.value == null)
			firebaseAuth.sendPasswordResetEmail(email.value!!)
				.addOnCompleteListener { resetEmailSent.value = it.isSuccessful }
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


}