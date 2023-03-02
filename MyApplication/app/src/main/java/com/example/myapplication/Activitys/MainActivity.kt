package com.example.myapplication.Activitys

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils.loadAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var _mainActivityBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = _mainActivityBinding.root
        setContentView(view)


        val options = ActivityOptions.makeCustomAnimation(this, R.anim.testanim, R.anim.testanim)
        _mainActivityBinding.btn1.setOnClickListener {
            val i = Intent(this,Activity2::class.java)
            startActivity(i,options.toBundle())

        }


    }








}



