package com.amian.donezo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amian.donezo.databinding.FragmentAddTodoBinding
import com.amian.donezo.viewmodels.AddTodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTodoFragment : BottomSheetDialogFragment() {

	private var _binding: FragmentAddTodoBinding? = null
	private val binding: FragmentAddTodoBinding
		get() = _binding!!

	private val viewModel: AddTodoViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentAddTodoBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// setup add button
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	companion object {
		const val TAG = "ADD_TODO_FRAGMENT"
	}
}