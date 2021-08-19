package com.amian.donezo.views.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amian.donezo.databinding.OnboardingFinalScreenBinding
import com.amian.donezo.databinding.OnboardingMiddleScreenBinding
import com.amian.donezo.databinding.OnboardingStartScreenBinding

class OnboardingScreenFragment : Fragment() {

	private val position by lazy {
		requireArguments().getInt(POSITION_KEY)
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		return when (position) {
			0 -> OnboardingStartScreenBinding.inflate(inflater, container, false).root
			1 -> OnboardingMiddleScreenBinding.inflate(inflater, container, false).root
			2 -> {
				with(OnboardingFinalScreenBinding.inflate(inflater, container, false)){

					button.setOnClickListener {
						findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment())
					}

					root
				}}
			else -> super.onCreateView(inflater, container, savedInstanceState)
		}
	}

	companion object {
		private const val POSITION_KEY = "position"

		fun newInstance(position: Int) = OnboardingScreenFragment().apply {
			arguments = bundleOf(
				POSITION_KEY to position
			)
		}
	}
}