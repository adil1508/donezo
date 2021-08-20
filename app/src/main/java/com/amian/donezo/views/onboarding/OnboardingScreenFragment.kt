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

	private val parent by lazy {
		parentFragment as OnboardingFragment
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		return when (position) {
			0 -> with(OnboardingStartScreenBinding.inflate(inflater, container, false)){
				rightArrow.setOnClickListener {
					parent.setCurrentItem(page = OnboardingFragment.Pages.MiddlePage)
				}
				root
			}
			1 -> with(OnboardingMiddleScreenBinding.inflate(inflater, container, false)){
				rightArrow.setOnClickListener {
					parent.setCurrentItem(OnboardingFragment.Pages.FinalPage)
				}
				leftArrow.setOnClickListener {
					parent.setCurrentItem(OnboardingFragment.Pages.StartPage)
				}
				root
			}
			2 -> {
				with(OnboardingFinalScreenBinding.inflate(inflater, container, false)){
					leftArrow.setOnClickListener {
						parent.setCurrentItem(OnboardingFragment.Pages.MiddlePage)
					}
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