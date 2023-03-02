package com.example.coroutines


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val name = "THREAD"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.btn1)

        btn.setOnClickListener {

            repeat(1000){
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d(name, Thread.currentThread().name)

                }
            }

        }
    }


    //start of test4
    //demonstrate how one other thread started its execution
    //when first get executed
    private fun test4() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(name, "1-${Thread.currentThread().name}")
            printnumberofuser2()
        }
    }

    private suspend fun printnumberofuser2() {
        var numberofuser = 0
        val job = CoroutineScope(Dispatchers.IO).async {
            getnumberofuser2()
        }
        Log.d(name, job.await().toString())
        Log.d(name, "2-${Thread.currentThread().name}")

    }

    private suspend fun getnumberofuser2(): Int {
        delay(1000)
        return 55
    }
    //end of test4


    //start of test3
    //demonstrate how one other thread started its execution
    //when first get executed
    private fun test3() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(name, "1-${Thread.currentThread().name}")
            printnumberofuser()


        }
    }


    private suspend fun printnumberofuser() {
        var numberofuser = 0
        val job = CoroutineScope(Dispatchers.IO).launch {
            numberofuser = getnumberofuser()
            Log.d(name, "2-${Thread.currentThread().name}")

        }
        job.join()
        //other code will run after this job is get done
        Log.d(name, "$numberofuser")
    }


    private suspend fun getnumberofuser(): Int {
        delay(1000)
        return 55
    }
    //end of test3


    //start of test2
    //function to demonstrate suspend point
    fun test2() {
        CoroutineScope(Dispatchers.IO).launch {
            task1()

        }
        CoroutineScope(Dispatchers.IO).launch {

            task2()
        }
    }

    suspend fun task1() {
        Log.d(name, "Start of task 1")
        yield()
        Log.d(name, "End of task 1")
    }

    suspend fun task2() {
        Log.d(name, "Start of task 2")
        yield()
        Log.d(name, "End of task 2")
    }
    //end of test2


    //start of test1
    //nomal coroutines
    fun test1() {
        MainScope().launch(Dispatchers.Main) {
            Log.d(name, "1-${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(name, "2-${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(name, "3-${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(name, "4-${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(name, "5-${Thread.currentThread().name}")
        }
    }
    //end of test1

}