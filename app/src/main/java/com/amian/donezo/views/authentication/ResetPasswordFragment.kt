package com.amian.donezo.views.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amian.donezo.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment: Fragment() {

	private var _binding: FragmentResetPasswordBinding? = null
	private val binding
		get() = _binding!!


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
		return binding.root
	}
}