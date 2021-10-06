package com.amian.donezo.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amian.donezo.databinding.FragmentAboutBinding

class AboutFragment : Fragment(), VersionHandler {

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
		(requireActivity() as AppCompatActivity).supportActionBar?.let {
			it.title = "About"
		}
		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	override fun getVersionCode(context: Context): String =
		context.packageManager.getPackageInfo(context.packageName, 0).versionName

}

interface VersionHandler {
	fun getVersionCode(context: Context): String
}