package com.amian.donezo.views.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amian.donezo.AuthenticationNavigationDirections
import com.amian.donezo.databinding.FragmentLoginBinding
import com.amian.donezo.viewmodels.authentication.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

	private var _binding: FragmentLoginBinding? = null
	private val binding
		get() = _binding!!

	private val viewModel: LoginViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.authenticated.observe(viewLifecycleOwner) {
			if (it) findNavController().navigate(AuthenticationNavigationDirections.actionAuthenticated())
		}

		binding.viewModel = viewModel

		binding.loginButton.setOnClickListener {
			viewModel.login()
		}

		viewModel.emailError.observe(viewLifecycleOwner) {
			binding.emailInput.error = it
			if (it == null) binding.emailInput.isErrorEnabled = false
		}

		viewModel.passwordError.observe(viewLifecycleOwner) {
			binding.passwordInput.error = it
			if (it == null) binding.passwordInput.isErrorEnabled = false
		}

		binding.forgotPassword.setOnClickListener {
			findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
		}

		binding.signupButton.setOnClickListener {
			findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
		}
	}
}