package com.amian.donezo.di

import android.content.Context
import androidx.room.Room
import com.amian.donezo.database.DonezoDatabase
import com.amian.donezo.database.dao.UserDao
import com.amian.donezo.repositories.UserRepoImpl
import com.amian.donezo.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DonezoProviderModule {

	@Provides
	@Singleton
	fun providesDatabase(@ApplicationContext context: Context): DonezoDatabase =
		Room.databaseBuilder(context, DonezoDatabase::class.java, "DonezoDatabase").build()

	@Provides
	fun providesUserDao(db: DonezoDatabase): UserDao = db.userDao()

}

@Module
@InstallIn(Singleton::class)
abstract class DonezoBindingModule {

	@Binds
	abstract fun bindsUserRepoImpl(userRepoImpl: UserRepoImpl): UserRepository
}