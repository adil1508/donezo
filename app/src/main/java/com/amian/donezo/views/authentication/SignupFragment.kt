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
		_binding = FragmentSignupBinding.inflate(inflater, container, false)

		binding.registerButton.setOnClickListener {
			viewModel.signUp(
				name = binding.nameInput.editText?.text.toString(),
				email = binding.emailInput.editText?.text.toString(),
				password = binding.passwordInput.editText?.text.toString()
			)
		}

		viewModel.authenticated.observe(viewLifecycleOwner, {
			if (it) findNavController().navigate(AuthenticationNavigationDirections.actionAuthenticated())
		})

		return binding.root
	}
}