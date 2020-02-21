package com.example.evento

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.FileOutputStream

class SettingActivity : AppCompatActivity() {

    private val fileName = "status_login.txt"
    lateinit var fileOutputStream: FileOutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to leave?")
            builder.setPositiveButton("Yes"){dialog, which ->
                fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOutputStream.write("0".toByteArray())
                fileOutputStream.close()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            builder.setNegativeButton("No"){
                    dialog, which ->
            }

            val dialog:AlertDialog = builder.create()
            dialog.show()
        }

        btnBackToProfile.setOnClickListener {

        }

    }

}
