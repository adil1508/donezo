package com.amian.donezo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amian.donezo.database.dao.UserDao
import com.amian.donezo.database.entities.User

@Database(entities = [User::class], version = 1)
abstract class DonezoDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao
}