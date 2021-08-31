package com.amian.donezo.views.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.amian.donezo.databinding.FragmentResetPasswordBinding
import com.amian.donezo.viewmodels.authentication.ResetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

	private var _binding: FragmentResetPasswordBinding? = null
	private val binding
		get() = _binding!!

	private val viewModel: ResetPasswordViewModel by viewModels()


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentResetPasswordBinding.inflate(inflater, container, false)

		binding.viewModel = viewModel

		viewModel.emailError.observe(viewLifecycleOwner){
			binding.emailInput.error = it
			if (it == null) binding.emailInput.isErrorEnabled = false
		}

		viewModel.resetEmailSent.observe(viewLifecycleOwner) { sent ->
			if (sent) {
				Toast.makeText(requireContext(), "Email sent to reset password", Toast.LENGTH_LONG).show()
				findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
			}
		}

		binding.resetPasswordButton.setOnClickListener {
			viewModel.resetPassword()
		}

		return binding.root
	}
}