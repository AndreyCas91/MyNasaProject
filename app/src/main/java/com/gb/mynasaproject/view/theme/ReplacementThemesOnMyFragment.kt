package com.gb.mynasaproject.view.theme

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.mynasaproject.databinding.FragmentReplacementThemesBinding
import com.gb.mynasaproject.view.GreenTheme
import com.gb.mynasaproject.view.MainActivity
import com.gb.mynasaproject.view.NeonTheme

class ReplacementThemesOnMyFragment : Fragment() {

    private var _binding: FragmentReplacementThemesBinding? = null
    val binding: FragmentReplacementThemesBinding
        get() {
            return _binding!!
        }

    private lateinit var parentActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity =
            requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReplacementThemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }

    private fun initButton() {
        binding.greenTheme.setOnClickListener {
            parentActivity.setCurrentTheme(GreenTheme)
            parentActivity.recreate()
        }

        binding.neonTheme.setOnClickListener {
            parentActivity.setCurrentTheme(NeonTheme)
            parentActivity.recreate()
        }
    }

    companion object {
        fun newInstance(): ReplacementThemesOnMyFragment {
            return ReplacementThemesOnMyFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}