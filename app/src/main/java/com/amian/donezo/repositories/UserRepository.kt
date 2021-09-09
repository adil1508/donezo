package com.amian.donezo.repositories

import androidx.lifecycle.LiveData
import com.amian.donezo.database.entities.User

interface UserRepository {

	val currentUser: LiveData<User?>

	suspend fun clearUser()

	suspend fun signup(name: String, email: String, password: String)

	suspend fun login(email: String, password: String)
}