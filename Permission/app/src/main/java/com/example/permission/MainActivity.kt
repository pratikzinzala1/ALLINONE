package com.example.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.permission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkpermissions()
    }

    private fun checkpermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS
                ),
                REQUEST_CODE
            )
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode.equals(REQUEST_CODE)) {

            for (i in grantResults) {

                if (i == PackageManager.PERMISSION_GRANTED) {

                } else {


                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Allow Required permission first")
                    builder.setTitle("Alert !")
                    builder.setCancelable(false)
                    builder.setPositiveButton("Yes") { dialog, which ->
                        finishAffinity()
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        checkpermissions()
                    }
                    builder.setNeutralButton("Cancel"){dailog,which->
                        dailog.cancel()
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                    break
                }

            }
        }

    }


}

