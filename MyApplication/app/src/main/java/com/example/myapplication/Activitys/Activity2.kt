package com.example.myapplication.Activitys


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.myapplication.databinding.Activity2Binding


class Activity2 : AppCompatActivity() {
    lateinit var binding: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val i = intent
        val uri: Uri? = i.getParcelableExtra(Intent.EXTRA_STREAM)

        Log.d("DATA",uri.toString())
        binding.imageview.setImageURI(uri)

    }
}