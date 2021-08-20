package com.amian.donezo.views.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amian.donezo.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

	private var _binding: FragmentOnboardingBinding? = null
	private val binding
		get() = _binding!!


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentOnboardingBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.pager.adapter = OnboardingPagerAdapter(this)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	fun setCurrentItem(page: Pages) {
		binding.pager.currentItem = page.ordinal
	}

	enum class Pages {
		StartPage,
		MiddlePage,
		FinalPage
	}

}

class OnboardingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

	override fun getItemCount() = 3

	override fun createFragment(page: Int) = OnboardingScreenFragment.newInstance(page)
}