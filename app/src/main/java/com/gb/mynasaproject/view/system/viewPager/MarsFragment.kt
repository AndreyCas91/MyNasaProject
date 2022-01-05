package com.gb.mynasaproject.view.system.viewPager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gb.mynasaproject.R
import com.gb.mynasaproject.databinding.FragmentMarsBinding
import com.gb.mynasaproject.viewmodel.AppState
import com.gb.mynasaproject.viewmodel.PictureOfTheDayViewModel

class MarsFragment:Fragment() {

    private var _binding: FragmentMarsBinding? = null
    val binding: FragmentMarsBinding
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
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMarsPicture()

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            rander(it)
        })
    }

    private fun rander(state : AppState){
        when(state){
            is AppState.Loading -> {
                binding.imageViewMars.load(R.drawable.bg_mars)
            }
            is AppState.SuccessMars -> {
                val url = state.marsPhotosServerResponseData.photos.first().imgSrc
                binding.imageViewMars.load(url)
            }
            is AppState.Error -> {
                val throwable = state.error
                Log.d("mylogs", "$throwable")
                Toast.makeText(context, "Ошибка загрузки фотографии", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
