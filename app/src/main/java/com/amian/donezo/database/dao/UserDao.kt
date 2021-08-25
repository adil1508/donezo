package com.amian.donezo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amian.donezo.database.entities.User
import com.amian.donezo.database.entities.User.Companion.USERS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insertUser(user: User)

	@Delete
	abstract suspend fun deleteUser(user: User)

	@Update
	abstract suspend fun updateUser(user: User)

	@Query("SELECT * FROM $USERS_TABLE LIMIT 1")
	abstract fun getCurrentUser(): Flow<User>

}