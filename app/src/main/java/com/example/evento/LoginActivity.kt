package com.example.evento

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.event_organizer.repository.RepoEventOrganizerPostgresql
import com.example.event_organizer.service.UserService
import com.example.evento.models.User
import com.example.evento.models.UserList
import com.example.evento.models.UserLogin
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

class LoginActivity : AppCompatActivity() {

    lateinit var email: TextView
    lateinit var password: TextView
    private val fileName = "status_login.txt"
    lateinit var fileOutputStream: FileOutputStream
    var dataUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        btnLogin.setOnClickListener {
            clickLoginUser()
        }
    }

    fun clickLoginUser() {
        val getServiceUser = RepoEventOrganizerPostgresql.CreateUserService()

        createUserService(getServiceUser)
    }

    fun createUserService(getServiceUser: UserService) {
        getServiceUser.getUserByEmail(UserLogin(email.text.toString(), password.text.toString()))
            .enqueue(object :
                Callback<UserList> {
                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    Log.e("tag", "errornya ${t.message}")
                }

                override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                    var data = response.body()
                    dataUser = data?.Users
                    if (data == null) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Incorrect email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    readFile(fileName)
                    Toast.makeText(this@LoginActivity, "Login Successfully", Toast.LENGTH_SHORT)
                        .show()

                }

            })
    }


    private fun readFile(fileName: String) {
        //Read file in strogae internal
        var fileInputStream: FileInputStream
        fileInputStream = openFileInput(fileName)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine();text }() != null) {
            stringBuilder.append(text)
        }
        if (stringBuilder.toString() == "0") {

            fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write("1".toByteArray())
            fileOutputStream.close()
            createNewFile()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun createNewFile(){
        //Create new file in storage internal
        try {
            fileOutputStream = openFileOutput("id_user.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(dataUser?.id.toString().toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
