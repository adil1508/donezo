package com.amian.donezo.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amian.donezo.repositories.UserRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepoImpl) : ViewModel() {


	private val currentUser = userRepo.observeUser()

	val authenticated = MutableLiveData(false)

	/*
	* TODO:
	*  - set a current user here that is observing the database
	*   - collect it, when not null, ask the fragment to navigate -> we've logged in
	*
	*  - Provide login() method
	*   - this will:
	* 		- call the firebase api to login
	* 			- on success, it will add the user to the database
	* 			(this is a bit complicated since we also will need to query firestore for info)
	*
	* */

	init {
		viewModelScope.launch {
			currentUser.distinctUntilChanged().collect {
				// TODO: tell the fragment to navigate
				if (it != null) authenticated.value = true
			}
		}
	}

	fun login(email: String, password: String) = viewModelScope.launch {
		userRepo.setUser(name = password, email = email)
	}

}