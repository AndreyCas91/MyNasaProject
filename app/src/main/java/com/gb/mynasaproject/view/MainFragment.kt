package com.gb.mynasaproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb.mynasaproject.R
import com.gb.mynasaproject.databinding.FragmentMainBinding
import com.gb.mynasaproject.view.picture.PictureOfTHeDayFragment
import com.gb.mynasaproject.view.system.SystemFragment
import com.gb.mynasaproject.view.theme.ReplacementThemesOnMyFragment

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState==null){
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container_fragment,
                PictureOfTHeDayFragment.newInstance()
            ).commit()
        }

        initBottomNavigation()

    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_calendar -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_fragment, PictureOfTHeDayFragment.newInstance())
                        .commit()
                    true
                }

                R.id.navigation_system -> {

                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_fragment, SystemFragment.newInstance())
                        .commit()
                    true
                }

                R.id.navigation_settings -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_fragment, ReplacementThemesOnMyFragment.newInstance())
                        .commit()
                    true
                }
                else -> {false}
            }
        }
    }

    companion object{
        fun newInstance() : MainFragment {
            return MainFragment()
        }
    }

}