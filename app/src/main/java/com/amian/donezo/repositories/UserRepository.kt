package com.amian.donezo.repositories

import com.amian.donezo.database.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

	suspend fun setUser(name: String, email: String)

	fun observeUser(): Flow<User?>
}