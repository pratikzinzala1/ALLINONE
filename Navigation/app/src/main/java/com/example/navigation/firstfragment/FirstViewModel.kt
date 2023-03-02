package com.example.navigation.firstfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel:ViewModel() {



    private val _textdata = MutableLiveData<String>()
    val textdata:LiveData<String>
           get() = _textdata
    private var number:Int = 0

    fun change(){

        number++
        _textdata.value = number.toString()
    }



}