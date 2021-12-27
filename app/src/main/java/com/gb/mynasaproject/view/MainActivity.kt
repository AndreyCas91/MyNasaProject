package com.gb.mynasaproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gb.mynasaproject.R
import com.gb.mynasaproject.view.picture.PictureOfTheDayFragment

const val GreenTheme = 1
const val NeonTheme = 2

class MainActivity : AppCompatActivity() {

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null){
            setTheme(getRealStyle(getCurrentTheme()))
        }

        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,
                PictureOfTheDayFragment.newInstance()).commit()
        }
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            GreenTheme -> R.style.GreenTheme
            NeonTheme -> R.style.NeonTheme
            else -> R.style.Theme_MyNasaProject
        }
    }
}