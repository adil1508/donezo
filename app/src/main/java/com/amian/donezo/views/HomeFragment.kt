package com.amian.donezo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amian.donezo.ApplicationNavigationDirections
import com.amian.donezo.R
import com.amian.donezo.databinding.FragmentHomeBinding
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	val binding
		get() = _binding!!

	@Inject
	lateinit var userRepository: UserRepository

	private val currentUser by lazy {
		userRepository.observeUser()
	}

	private val todoFragment by lazy {
		AddTodoFragment()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		currentUser.asLiveData().observe(viewLifecycleOwner, {
			if (it == null) findNavController().navigate(ApplicationNavigationDirections.actionUnauthenticated())
			else {
				val newstr = "All Donezo, " + it.name + "!"
				binding.text.text = newstr
			}
		})

		binding.logoutButton.setOnClickListener {
			lifecycleScope.launch { userRepository.clearUser() }
		}

		with(requireActivity() as AppCompatActivity) {
			setSupportActionBar(binding.toolbar)
			supportActionBar?.setDisplayShowTitleEnabled(false)
		}

		setHasOptionsMenu(true)

		return binding.root
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.home_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.add -> {
				todoFragment.show(requireActivity().supportFragmentManager, AddTodoFragment.TAG)
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}