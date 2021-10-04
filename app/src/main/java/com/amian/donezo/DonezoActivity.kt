package com.amian.donezo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amian.donezo.databinding.ActivityMainBinding
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DonezoActivity : AppCompatActivity() {

	@Inject
	lateinit var userRepository: UserRepository

	lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setSupportActionBar(binding.toolbar)
		setContentView(binding.root)
	}
}