package com.amian.donezo.di

import android.content.Context
import androidx.room.Room
import com.amian.donezo.database.DonezoDatabase
import com.amian.donezo.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DonezoModule {

	@Provides
	@Singleton
	fun providesDatabase(@ApplicationContext context: Context): DonezoDatabase =
		Room.databaseBuilder(context, DonezoDatabase::class.java, "DonezoDatabase").build()

	@Provides
	fun providesUserDao(db: DonezoDatabase): UserDao = db.userDao()

}