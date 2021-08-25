package com.amian.donezo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
	@PrimaryKey(autoGenerate = true) val id: Int,
	val name: String,
	val email: String
) {
	constructor(name: String, email: String) : this(0, name, email)
}