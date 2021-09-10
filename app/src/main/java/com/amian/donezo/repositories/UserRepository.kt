package com.amian.donezo.repositories

import com.amian.donezo.database.entities.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

	val currentUser: StateFlow<User?>

	suspend fun clearUser()

	suspend fun signup(name: String, email: String, password: String)

	suspend fun login(email: String, password: String)
}