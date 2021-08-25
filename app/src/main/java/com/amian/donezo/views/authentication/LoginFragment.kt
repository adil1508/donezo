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

		binding.loginButton.setOnClickListener {
			viewModel.login(
				email = binding.emailInput.editText?.text.toString(),
				password = binding.passwordInput.editText?.text.toString()
			)
		}

		viewModel.authenticated.observe(requireActivity(), {
			if (it) findNavController().navigate(AuthenticationNavigationDirections.actionAuthenticated())
		})

		return binding.root
	}
}