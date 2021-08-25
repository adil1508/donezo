package com.amian.donezo.di

import android.content.Context
import androidx.room.Room
import com.amian.donezo.database.DonezoDatabase
import com.amian.donezo.database.dao.UserDao
import com.amian.donezo.repositories.UserRepoImpl
import com.amian.donezo.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
	@Singleton
	fun providesUserDao(db: DonezoDatabase): UserDao = db.userDao()

	@Provides
	@Singleton
	fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

	@Provides
	@Singleton
	fun providesFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DonezoBindingModule {

	@Binds
	abstract fun bindsUserRepoImpl(userRepoImpl: UserRepoImpl): UserRepository
}