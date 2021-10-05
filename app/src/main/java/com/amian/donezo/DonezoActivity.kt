package com.amian.donezo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amian.donezo.databinding.ActivityMainBinding
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DonezoActivity : AppCompatActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    lateinit var binding: ActivityMainBinding

    lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Setup navigation controller
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        appBarConfig = AppBarConfiguration(setOf(R.id.homeFragment), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)
        binding.navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navigationController = findNavController(R.id.nav_host_fragment)
        return navigationController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

}