package com.gb.mynasaproject.view.system.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb.mynasaproject.databinding.FragmentVenusBinding
import com.gb.mynasaproject.viewmodel.PictureOfTheDayViewModel

class VenusFragment:Fragment() {

    private var _binding: FragmentVenusBinding? = null
    val binding: FragmentVenusBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVenusBinding.inflate(inflater, container, false)
        return binding.root
    }
}