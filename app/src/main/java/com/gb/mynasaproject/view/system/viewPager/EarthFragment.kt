package com.gb.mynasaproject.view.system.viewPager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.gb.mynasaproject.BuildConfig
import com.gb.mynasaproject.R
import com.gb.mynasaproject.databinding.FragmentEarthBinding
import com.gb.mynasaproject.viewmodel.AppState
import com.gb.mynasaproject.viewmodel.PictureOfTheDayViewModel

class EarthFragment:Fragment() {

    private var isExpand = false

    private var _binding: FragmentEarthBinding? = null
    val binding: FragmentEarthBinding
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
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEpic()

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            rander(it)
        })

        imageViewScale()
    }

    private fun imageViewScale() {
        binding.imageViewEarth.setOnClickListener {
            isExpand = !isExpand

            val params = binding.imageViewEarth.layoutParams as LinearLayout.LayoutParams

            val transitionSet = TransitionSet()
            val transitionCB = ChangeBounds()
            val transitionImage = ChangeImageTransform()
            transitionCB.duration = 2000
            transitionImage.duration = 2000
            transitionSet.addTransition(transitionCB)
            transitionSet.addTransition(transitionImage)
            TransitionManager.beginDelayedTransition(binding.container, transitionSet)

            if (isExpand) {
                binding.imageViewEarth.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                binding.imageViewEarth.scaleType = ImageView.ScaleType.CENTER
            }

            binding.imageViewEarth.layoutParams = params
        }
    }

    private fun rander(state: AppState){
        when(state){
            is AppState.Loading -> {
                binding.imageViewEarth.load(R.drawable.bg_earth)
            }
            is AppState.SuccessEarthEpic -> {
                val strDate = state.earthPhotoResponseData.last().earth_date!!.split(" ").first()
                val image = state.earthPhotoResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-","/",true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.imageViewEarth.load(url)
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