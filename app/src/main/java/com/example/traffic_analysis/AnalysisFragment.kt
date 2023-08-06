package com.example.traffic_analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.traffic_analysis.databinding.FragmentAnalysisBinding

private const val EXTRA_KEY_DETAIL = "com.example.detail"
class AnalysisFragment : Fragment() {
    private lateinit var binding: FragmentAnalysisBinding

    companion object {
        const val TAG = "com.example:fragment"
        @JvmStatic
        fun newInstance(detail: String) = AnalysisFragment()
            .apply {
                Bundle().apply {
                    putString(EXTRA_KEY_DETAIL, detail)
                }.also { arguments = it }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisBinding.inflate(layoutInflater,container,false).apply {
            tvDetail.text = arguments?.getString(EXTRA_KEY_DETAIL)
        }
        return binding.root
    }

}