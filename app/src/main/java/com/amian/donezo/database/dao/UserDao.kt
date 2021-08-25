package com.amian.donezo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.amian.donezo.database.entities.User

@Dao
abstract class UserDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insertUser(user: User)

	@Delete
	abstract suspend fun deleteUser(user: User)

	@Update
	abstract suspend fun updateUser(user: User)

}