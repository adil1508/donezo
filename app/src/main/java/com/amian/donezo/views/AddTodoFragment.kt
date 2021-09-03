package com.amian.donezo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amian.donezo.databinding.FragmentAddTodoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTodoFragment : BottomSheetDialogFragment() {

	private var _binding: FragmentAddTodoBinding? = null
	val binding: FragmentAddTodoBinding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentAddTodoBinding.inflate(inflater, container, false)

		return binding.root
	}

	companion object {
		const val TAG = "ADD_TODO_FRAGMENT"
	}
}