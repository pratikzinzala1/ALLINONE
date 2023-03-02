package com.pratik.filedownloader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import com.example.myapplication.utils.TestService
import com.pratik.filedownloader.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.activity = this

    }

    fun download() {
        val i = Intent(this,TestService::class.java)
        i.putExtra("URL", linkvideo2)
        applicationContext.startForegroundService(i)

    }



}