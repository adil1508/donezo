package com.amian.donezo.repositories

import com.amian.donezo.Constants.Companion.FIRESTORE_USERS_EMAIL_KEY
import com.amian.donezo.Constants.Companion.FIRESTORE_USERS_NAME_KEY
import com.amian.donezo.Constants.Companion.FIRESTORE_USERS_TABLE
import com.amian.donezo.database.dao.UserDao
import com.amian.donezo.database.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
	private val userDao: UserDao,
	private val firebaseAuth: FirebaseAuth,
	firestore: FirebaseFirestore
) : UserRepository {

	override val currentUser =
		userDao.observeCurrentUser().stateIn(GlobalScope, SharingStarted.Eagerly, null)

	private val usersCollection = firestore.collection(FIRESTORE_USERS_TABLE)

	override suspend fun clearUser() = userDao.deleteUser()

	override suspend fun login(email: String, password: String) {
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
			if (it.isSuccessful) {
				GlobalScope.launch(Dispatchers.IO) {
					getUserInfoFromFirestore(email = email)
				}
			} else {
				Timber.e("Could not login user!")
			}
		}
	}

	override suspend fun signup(name: String, email: String, password: String) {
		firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
			if (it.isSuccessful) {
				GlobalScope.launch(Dispatchers.IO) {
					setUserInfoInFirestore(email = email, name = name)
				}
			} else {
				Timber.e("Could not sign up user!")
			}
		}
	}

	private suspend fun setUser(name: String, email: String) =
		userDao.insertUser(User(email = email, name = name))

	private suspend fun getUserInfoFromFirestore(email: String) {
		usersCollection.whereEqualTo(FIRESTORE_USERS_EMAIL_KEY, email)
			.limit(1).get()
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					// write to local db
					task.result?.documents?.let {
						val document = it[0]
						GlobalScope.launch(Dispatchers.IO) {
							setUser(
								email = email,
								name = document.getString(FIRESTORE_USERS_NAME_KEY)!!
							)
						}
					}
				} else {
					Timber.e("Failed to retrieve user info from Firestore!")
				}
			}
	}

	private suspend fun setUserInfoInFirestore(email: String, name: String) {
		usersCollection
			.add(hashMapOf(FIRESTORE_USERS_NAME_KEY to name, FIRESTORE_USERS_EMAIL_KEY to email))
			.addOnCompleteListener {
				if (it.isSuccessful) {
					GlobalScope.launch(Dispatchers.IO) {
						setUser(name = name, email = email)
					}
				} else {
					Timber.e("Could not write user to Firestore!")
				}
			}
	}
}