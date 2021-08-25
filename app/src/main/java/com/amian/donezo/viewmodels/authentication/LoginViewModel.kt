package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.ViewModel
import com.amian.donezo.repositories.UserRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepoImpl) : ViewModel() {

	fun test() {
		Timber.d("Huzzah!")
	}

}