package com.amian.donezo.views.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amian.donezo.AuthenticationNavigationDirections
import com.amian.donezo.databinding.FragmentSignupBinding
import com.amian.donezo.viewmodels.authentication.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

	private var _binding: FragmentSignupBinding? = null
	private val binding
		get() = _binding!!

	private val viewModel: SignupViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		viewModel.authenticated.observe(viewLifecycleOwner) {
			if (it) findNavController().navigate(AuthenticationNavigationDirections.actionAuthenticated())
		}

		_binding = FragmentSignupBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.viewModel = viewModel

		viewModel.emailError.observe(viewLifecycleOwner) {
			binding.emailInput.error = it
			if (it == null) binding.emailInput.isErrorEnabled = false
		}

		viewModel.nameError.observe(viewLifecycleOwner) {
			binding.nameInput.error = it
			if (it == null) binding.nameInput.isErrorEnabled = false
		}

		viewModel.passwordError.observe(viewLifecycleOwner) {
			binding.passwordInput.error = it
			if (it == null) binding.passwordInput.isErrorEnabled = false
		}

		viewModel.confirmedPasswordError.observe(viewLifecycleOwner) {
			binding.reenterPasswordInput.error = it
			if (it == null) binding.reenterPasswordInput.isErrorEnabled = false
		}

		binding.registerButton.setOnClickListener {
			viewModel.signUp()
		}

	}
}