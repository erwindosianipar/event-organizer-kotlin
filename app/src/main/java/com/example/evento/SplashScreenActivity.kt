package com.example.evento

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_DELAY :Long = 1000
    private val fileName = "status_login.txt"
    lateinit var fileOutputStream: FileOutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            if (!fileExist(this,this.fileName)){
                createNewFile()
                readFile(this.fileName)
            }else {
                readFile(this.fileName)
            }
        },SPLASH_DELAY)

    }

    private fun createNewFile(){
        //Create new file in storage internal
        try {
            fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write("0".toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readFile(fileName: String) {
        //Read file in strogae internal
        var fileInputStream: FileInputStream? = null
        fileInputStream = openFileInput(fileName)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine();text }() != null) {
            stringBuilder.append(text)
        }
        Log.d("INI SPLASH",stringBuilder.toString())
        if (stringBuilder.toString() == "0"){
            startActivity(Intent(this,SlideIntroActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    fun fileExist(context: Context, fileName:String): Boolean {
        var file : File = context.getFileStreamPath(fileName)
        if (file == null || !file.exists()){
            return false
        }
        return true
    }
}
