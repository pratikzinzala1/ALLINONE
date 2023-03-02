package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("TEST", "Activitys oncreat")


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_container) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.topBar)
        setupActionBarWithNavController(navController)


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TEST", "Activitys onrestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST", "Activitys onstart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST", "Activitys onresume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST", "Activitys onpause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST", "Activitys onstop")
    }


}