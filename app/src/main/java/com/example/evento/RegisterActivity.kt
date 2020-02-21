package com.example.evento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.event_organizer.repository.RepoEventOrganizerPostgresql
import com.example.event_organizer.service.UserService
import com.example.evento.models.User
import com.example.evento.models.UserRegister
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.txtRegEmail)
        password = findViewById(R.id.txtRegPassword)
        name = findViewById(R.id.txtRegName)


        txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    fun clickRegisterUser(view: View) {
        val postServiceUser = RepoEventOrganizerPostgresql.CreateUserService()

        createServiceUser(postServiceUser)
    }

    private fun createServiceUser(postServiceUser: UserService) {
        postServiceUser.addUser(
            UserRegister(
                email.text.toString(),
                password.text.toString(),
                name.text.toString()
            )
        ).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("tag", "errornya ${t.message}")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                email.setText("")
                password.setText("")
                name.setText("")
                if (response.code() == 201) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Created Account Success",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Created Account Failed, Email already use",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })
    }
}
