package com.gb.mynasaproject.view.picture

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.gb.mynasaproject.R
import com.gb.mynasaproject.databinding.BottomNavigationLayoutBinding
import com.gb.mynasaproject.view.theme.ReplacementThemesOnMy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() {
            return _binding!!
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.replacementTheme -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, ReplacementThemesOnMy.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.navigation_two -> {
                    Toast.makeText(context,"2",Toast.LENGTH_SHORT).show()
                }
            }

            true
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


}