package com.amian.donezo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amian.donezo.ApplicationNavigationDirections
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

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		userRepository.observeUser().asLiveData().observe(viewLifecycleOwner, {
			if (it == null) findNavController().navigate(ApplicationNavigationDirections.actionUnauthenticated())
		})

		_binding = FragmentHomeBinding.inflate(inflater, container, false)

		binding.logoutButton.setOnClickListener {
			lifecycleScope.launch { userRepository.clearUser() }
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}