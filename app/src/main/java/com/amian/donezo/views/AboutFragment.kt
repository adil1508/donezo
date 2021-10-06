package com.amian.donezo.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amian.donezo.databinding.FragmentAboutBinding

class AboutFragment : Fragment(), versionHandler {

	private var _binding: FragmentAboutBinding? = null
	private val binding: FragmentAboutBinding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAboutBinding.inflate(inflater, container, false)
		binding.versionHandler = this
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	override fun getVersionCode(context: Context): String =
		context.packageManager.getPackageInfo(context.packageName, 0).versionName

}

interface versionHandler {
	fun getVersionCode(context: Context): String
}