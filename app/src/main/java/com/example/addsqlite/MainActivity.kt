package com.example.addsqlite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*


class MainActivity : AppCompatActivity(), Validator.ValidationListener {
    val database = Database(this)

    private var btnRegister: Button? = null
    var btnLogin : Button? = null


    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.Email
    var username: EditText? = null
//
    @NotEmpty
    @Password
    @Pattern(regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
    var password: EditText? = null
//
    @ConfirmPassword
    var confirmPassword: EditText? = null
//
//    var btn_register: Button? = null, var btn_login:Button? = null
//    var validator: Validator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin = findViewById<Button>(R.id.btn_login)
        btnRegister = findViewById<Button>(R.id.btn_register)
        username = findViewById<EditText>(R.id.et_username)
        password = findViewById<EditText>(R.id.et_password)
        confirmPassword = findViewById<EditText>(R.id.et_cpassword)

        btnLogin?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        btnRegister?.setOnClickListener(View.OnClickListener { v ->
//            buttonSave_onClick(v)
//
            val usernameValue: String = username?.text.toString()
            val passwordValue: String = password?.text.toString()
            val confirm_password: String = confirmPassword?.text.toString()

            if (usernameValue == "" || passwordValue == "" || confirm_password == "") {
                Toast.makeText(applicationContext, "Fields Required", Toast.LENGTH_SHORT).show()
            } else {
                if (passwordValue == confirm_password) {

                    val checkUserName: Boolean = database.checkUserName(usernameValue)
                    if (!checkUserName) {
                        val insert: Boolean = database.insert(usernameValue, passwordValue)

                        if (insert) {
                            Toast.makeText(applicationContext, "Registered", Toast.LENGTH_SHORT)
                                .show()
                            username?.setText("")
                            password?.setText("")
                            confirmPassword?.setText("")
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Username already taken",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Password does not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onValidationSucceeded() {
        Toast.makeText(this, "We got it right!", Toast.LENGTH_SHORT).show()
    }

    override fun onValidationFailed(errors: List<ValidationError?>) {
        for (error in errors) {
            val view: View = error?.getView() as View
            val message: String = error.getCollatedErrorMessage(this) as String
            // Display error messages
            if (view is EditText) {
                view.error = message
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}