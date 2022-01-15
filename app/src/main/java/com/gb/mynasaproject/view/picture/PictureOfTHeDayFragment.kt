package com.gb.mynasaproject.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.gb.mynasaproject.R
import com.gb.mynasaproject.databinding.FragmentPictureOfTheDayStartBinding
import com.gb.mynasaproject.viewmodel.AppState
import com.gb.mynasaproject.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTHeDayFragment : Fragment() {

    private var isExpand = false

    private var _binding: FragmentPictureOfTheDayStartBinding? = null
    val binding: FragmentPictureOfTheDayStartBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendServerRequest(takeDate(0))

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        initButton()

        animationImageView()
    }

    private fun animationImageView() {
        binding.imageView.setOnClickListener {
            isExpand = !isExpand

            val params = binding.imageView.layoutParams as ConstraintLayout.LayoutParams

            val transitionSet = TransitionSet()
            val transitionCB = ChangeBounds()
            val transitionImage = ChangeImageTransform()
            transitionCB.duration = 2000
            transitionImage.duration = 2000
            transitionSet.addTransition(transitionCB)
            transitionSet.addTransition(transitionImage)
            TransitionManager.beginDelayedTransition(binding.container, transitionSet)

            if (isExpand) {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            } else {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            }

            binding.imageView.layoutParams = params
        }
    }

    private fun initButton() {
        binding.btnDay.setOnClickListener {
            viewModel.sendServerRequest(takeDate(0))
        }

        binding.btnYesterday.setOnClickListener {
            viewModel.sendServerRequest(takeDate(-1))
        }

        binding.btnBeforeYesterday.setOnClickListener {
            viewModel.sendServerRequest(takeDate(-2))
        }

        binding.btnTextVisibility.setOnClickListener {

            val transition = TransitionSet()

            transition.addTransition(Fade())
            transition.addTransition(AutoTransition())
            transition.duration = 1000

            TransitionManager.beginDelayedTransition(binding.container, transition)

            binding.textView.visibility = View.VISIBLE
            binding.btnTextVisibility.visibility = View.GONE

        }
    }

    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                val throwable = state.error
                Log.d("mylogs", "$throwable")
                Toast.makeText(context, "Ошибка загрузки фотографии", Toast.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is AppState.SuccessDay -> {
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                binding.imageView.load(url) {
                    lifecycle(this@PictureOfTHeDayFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }

                val explanation = pictureOfTheDayResponseData.explanation
                binding.textView.text = explanation
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): PictureOfTHeDayFragment {
            return PictureOfTHeDayFragment()
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

}