package com.amian.donezo.views.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amian.donezo.AuthenticationNavigationDirections
import com.amian.donezo.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

	private var _binding: FragmentSignupBinding? = null
	private val binding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSignupBinding.inflate(inflater, container, false)

		binding.registerButton.setOnClickListener {
			findNavController().navigate(AuthenticationNavigationDirections.actionAuthenticated())
		}

		return binding.root
	}
}