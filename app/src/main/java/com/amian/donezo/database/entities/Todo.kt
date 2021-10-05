package com.amian.donezo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amian.donezo.database.entities.Todo.Companion.TODO_TABLE

@Entity(tableName = TODO_TABLE)
data class Todo(
	val email: String,
	val todo: String,
	val done: Boolean
) {
	@PrimaryKey(autoGenerate = true)
	var id: Long = 0

	constructor(id: Long, email: String, todo: String, done: Boolean) : this(email, todo, done) {
		this.id = id
	}

	companion object {
		const val TODO_TABLE = "Todos"
	}
}