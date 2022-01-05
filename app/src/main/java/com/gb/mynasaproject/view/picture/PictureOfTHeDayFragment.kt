package com.gb.mynasaproject.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gb.mynasaproject.R
import com.gb.mynasaproject.databinding.FragmentPictureOfTheDayBinding
import com.gb.mynasaproject.viewmodel.AppState
import com.gb.mynasaproject.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTHeDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    val binding: FragmentPictureOfTheDayBinding
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
//        viewModel.sendServerRequest()
        viewModel.sendServerRequest(takeDate(0))

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }


         binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
             when(checkedId){
                 R.id.chipToday -> {
                     viewModel.sendServerRequest(takeDate(0))
                 }

                 R.id.chipYesterday -> {
                     viewModel.sendServerRequest(takeDate(-1))
                 }

                 R.id.chipBeforeYesterday -> {
                     viewModel.sendServerRequest(takeDate(-2))
                 }
             }
         }

//        val behavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)

//        behavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                }
//            }

//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                Log.d("mylogs", "$slideOffset slideOffset")
//            }
//        })

//        setBottomAppBar()

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
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu_bottom_bar, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
//            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
//                .replace(
//                    R.id.container,
//                    ChipsFragment.newInstance()
//                )
//                .addToBackStack(null)
//                .commit()
//            android.R.id.home -> BottomNavigationDrawerFragment().show(
//                requireActivity().supportFragmentManager,
//                ""
//            )
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    private var isMain = true

//    private fun setBottomAppBar() {
//        val context = activity as MainActivity
//        context.setSupportActionBar(binding.bottomAppBar)
//        setHasOptionsMenu(true)
//
//        binding.fab.setOnClickListener {
//            if (isMain) {
//                isMain = false
//                binding.bottomAppBar.navigationIcon = null
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.ic_back_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
//            } else {
//                isMain = true
//                binding.bottomAppBar.navigationIcon =
//                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.ic_plus_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
//            }
//        }

//    }

}