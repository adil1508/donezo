package com.amian.donezo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amian.donezo.database.entities.User.Companion.USERS_TABLE

@Entity(tableName = USERS_TABLE)
data class User(
	@PrimaryKey val email: String,
	val name: String
) {
	companion object {
		const val USERS_TABLE = "Users"
	}
}