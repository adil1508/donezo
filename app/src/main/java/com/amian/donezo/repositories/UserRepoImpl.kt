package com.amian.donezo.repositories

import com.amian.donezo.database.dao.UserDao
import com.amian.donezo.database.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userDao: UserDao) : UserRepository {

	override suspend fun setUser(name: String, email: String) =
		userDao.insertUser(User(name = name, email = email))

	override fun observeUser(): Flow<User?> = userDao.getCurrentUser()
}