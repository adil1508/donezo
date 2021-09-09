package com.amian.donezo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.amian.donezo.database.entities.User
import com.amian.donezo.database.entities.User.Companion.USERS_TABLE

@Dao
interface UserDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertUser(user: User)

	@Delete
	suspend fun deleteUser(user: User)

	@Update
	suspend fun updateUser(user: User)

	@Query("SELECT * FROM $USERS_TABLE LIMIT 1")
	fun observeCurrentUser(): LiveData<User?>

	@Query("DELETE FROM $USERS_TABLE")
	suspend fun deleteUser()

}