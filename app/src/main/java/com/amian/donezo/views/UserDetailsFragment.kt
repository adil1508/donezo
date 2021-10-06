package com.amian.donezo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.amian.donezo.R
import com.amian.donezo.databinding.FragmentUserDetailsBinding
import com.amian.donezo.repositories.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

	@Inject
	lateinit var userRepository: UserRepository

	private val currentUser by lazy {
		userRepository.currentUser.asLiveData()
	}

	private var _binding: FragmentUserDetailsBinding? = null
	private val binding: FragmentUserDetailsBinding
		get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		currentUser.observe(viewLifecycleOwner) {
			it?.let { user ->
				binding.user = user
			}
		}

		(requireActivity() as AppCompatActivity).supportActionBar?.let {
			it.title = "Profile"
		}

	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		menu.clear()
		inflater.inflate(R.menu.profile_menu, menu)
	}
}