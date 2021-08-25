package com.amian.donezo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amian.donezo.database.entities.User.Companion.USERS_TABLE

@Entity(tableName = USERS_TABLE)
data class User(
	@PrimaryKey(autoGenerate = true) val id: Int,
	val name: String,
	val email: String
) {
	constructor(name: String, email: String) : this(0, name, email)

	companion object {
		const val USERS_TABLE = "Users"
	}
}