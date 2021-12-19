package com.gb.mynasaproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gb.mynasaproject.R
import com.gb.mynasaproject.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,
                PictureOfTheDayFragment.newInstance()).commit()
        }
    }
}