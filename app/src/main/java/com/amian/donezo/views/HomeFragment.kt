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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amian.donezo.ApplicationNavigationDirections
import com.amian.donezo.R
import com.amian.donezo.databinding.FragmentHomeBinding
import com.amian.donezo.repositories.UserRepository
import com.amian.donezo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	val binding
		get() = _binding!!

	@Inject
	lateinit var userRepository: UserRepository

	private val todoFragment by lazy {
		AddTodoFragment()
	}

	private val viewModel: HomeViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		viewModel.authenticated.observe(viewLifecycleOwner) {
			if (!it) findNavController().navigate(ApplicationNavigationDirections.actionUnauthenticated())
		}

		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		userRepository.currentUser.value?.let {
			val newstr = "All Donezo, " + it.name + "!"
			binding.text.text = newstr
			viewModel.todos.observe(viewLifecycleOwner, {
				Timber.d("The size of the todos list is: ${it.size}")
			})
		}

		binding.logoutButton.setOnClickListener {
			lifecycleScope.launch { userRepository.clearUser() }
		}

		with(requireActivity() as AppCompatActivity) {
			setSupportActionBar(binding.toolbar)
			supportActionBar?.setDisplayShowTitleEnabled(false)
		}

		setHasOptionsMenu(true)
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