package com.amian.donezo.views.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amian.donezo.AuthenticationNavigationDirections
import com.amian.donezo.databinding.OnboardingFinalScreenBinding
import com.amian.donezo.databinding.OnboardingMiddleScreenBinding
import com.amian.donezo.databinding.OnboardingStartScreenBinding
import com.amian.donezo.views.onboarding.OnboardingFragment.Pages.*

class OnboardingScreenFragment : Fragment() {

	private val page by lazy {
		requireArguments().getInt(page_key)
	}

	private val parent by lazy {
		parentFragment as OnboardingFragment
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		return when (page) {
			StartPage.ordinal -> with(OnboardingStartScreenBinding.inflate(inflater, container, false)){
				rightArrow.setOnClickListener {
					parent.setCurrentItem(page = MiddlePage)
				}
				root
			}
			MiddlePage.ordinal -> with(OnboardingMiddleScreenBinding.inflate(inflater, container, false)){
				rightArrow.setOnClickListener {
					parent.setCurrentItem(FinalPage)
				}
				leftArrow.setOnClickListener {
					parent.setCurrentItem(StartPage)
				}
				root
			}
			FinalPage.ordinal -> {
				with(OnboardingFinalScreenBinding.inflate(inflater, container, false)){
					leftArrow.setOnClickListener {
						parent.setCurrentItem(MiddlePage)
					}
					button.setOnClickListener {
						findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment())
					}
					root
				}}
			else -> super.onCreateView(inflater, container, savedInstanceState)
		}
	}

	companion object {
		private const val page_key = "page"

		fun newInstance(position: Int) = OnboardingScreenFragment().apply {
			arguments = bundleOf(
				page_key to position
			)
		}
	}
}