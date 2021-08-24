package com.amian.donezo.views.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amian.donezo.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

	private var _binding: FragmentLoginBinding? = null
	private val binding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentLoginBinding.inflate(inflater, container, false)

		binding.loginButton.setOnClickListener {
			findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
		}

		return binding.root
	}
}