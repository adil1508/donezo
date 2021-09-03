package com.amian.donezo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amian.donezo.database.entities.Todo.Companion.TODO_TABLE

@Entity(tableName = TODO_TABLE)
data class Todo(
	@PrimaryKey val email: String,
	val todo: String
) {
	companion object {
		const val TODO_TABLE = "Todos"
	}
}