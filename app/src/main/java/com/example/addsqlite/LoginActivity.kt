package com.example.addsqlite


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    val database = Database(this)

    private var btnRegister: Button? = null
    var btnLogin : Button? = null

    var userName : EditText? = null
    var password : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById<Button>(R.id.login_button)
        btnRegister = findViewById<Button>(R.id.register_button)
        userName = findViewById<EditText>(R.id.et_username)
        password = findViewById<EditText>(R.id.et_password)

        btnLogin?.setOnClickListener(View.OnClickListener { v ->

            Log.i("Singh", "here 1")
            val usernameValue: String = userName?.text.toString()
            val passwordValue: String = password?.text.toString()

            if (usernameValue == "" || passwordValue == "") {
                Toast.makeText(applicationContext, "Credentials Required", Toast.LENGTH_LONG)
                    .show();
            }else {
                Log.i("Singh", "here 2")
                val checkLogin: Boolean = database.checkLogin(usernameValue, passwordValue)

                Log.i("Singh", "here 3")
                if (checkLogin) {

                    Log.i("Singh", "here 4")
                    val i = Intent(applicationContext, WelcomeActivity::class.java)
                    startActivity(i)

                } else {
                    Log.i("Singh", "here 5")
                    Toast.makeText(
                        applicationContext,
                        "Invalid username or password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        btnRegister?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}