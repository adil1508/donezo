package com.amian.donezo.repositories

import com.amian.donezo.database.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

	fun observeUser(): Flow<User?>

	suspend fun signup(name: String, email: String, password: String)

	suspend fun login(email: String, password: String)
}