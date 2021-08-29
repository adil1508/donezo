package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) :
	ViewModel() {

	val resetEmailSent = MutableLiveData(false)

	fun resetPassword(email: String) =
		firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
			resetEmailSent.value = it.isSuccessful
		}

}