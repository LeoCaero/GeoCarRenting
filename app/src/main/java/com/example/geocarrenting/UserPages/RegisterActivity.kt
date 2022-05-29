package com.example.geocarrenting.UserPages

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.geocarrenting.API.RestAPIService
import com.example.geocarrenting.API.model.Car
import com.example.geocarrenting.API.model.User
import com.example.geocarrenting.MainActivity
import com.example.geocarrenting.R
import com.example.geocarrenting.databinding.ActivityMainBinding
import com.example.geocarrenting.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText


class RegisterActivity : AppCompatActivity() {
    var allInputValidated: Boolean = false
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        //WORK SPACE //

        var btnSignup = binding.buttonSignup

        var inputName = binding.etName
        var labelValidateName = binding.txtViewValidateName

        var isValidName = false

        var inputEmail = binding.etEmail
        var labelValidateEmail = binding.txtViewValidateEmail
        var isValidEmail = false

        var inputPassword = binding.etPassword

        var labelValidatePasswordRequire = binding.txtViewValidatePasswordRequired
        var labelValidatePasswordSpecial = binding.txtViewValidatePasswordSpecial
        var labelValidatePasswordLength = binding.txtViewValidatePasswordLength
        var isValidPassword = false
        var len = 0
        inputName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                isValidName = validateName(inputName, labelValidateName)
            }
        }

        inputEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                isValidEmail = validateEmail(inputEmail, labelValidateEmail)
            }
        }

        inputPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                len = inputPassword.text!!.length
                labelValidatePasswordLength.setText("$len/8")
                isValidPassword = validatePassword(
                    inputPassword,
                    labelValidatePasswordRequire,
                    labelValidatePasswordSpecial,
                    labelValidatePasswordLength,
                    len
                )
            }
        }

        inputPassword.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            len = inputPassword.text!!.length
            labelValidatePasswordLength.setText("$len/8")
            isValidPassword = validatePassword(
                inputPassword,
                labelValidatePasswordRequire,
                labelValidatePasswordSpecial,
                labelValidatePasswordLength,
                len
            )
            false
        })

        binding.buttonSignup.setOnClickListener {
            inputName.clearFocus()
            inputEmail.clearFocus()
            inputPassword.clearFocus()


            //DEFINITIVO
            if (validateAllInputs(isValidName, isValidEmail, isValidPassword)) {
                signup(inputName.text.toString(), inputEmail.text.toString(), inputPassword.text.toString())
            }

        }

    }

    private fun validateInputs(name: EditText, email: EditText, password: EditText): Boolean {

        if (name.length() == 0) {
            name.setError("Username is required")
            return false
        }

        if (email.length() == 0) {
            email.setError("Email is required")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.setError("Email format not valid")
            return false
        }

        if (password.length() == 0) {
            password.setError("Password is required")
            return false
        } else if (password.length() < 8) {
            password.setError("Password must be at least 8 characters")
            return false
        } else if (!password.text.matches(".*[^A-Za-z0-9].*".toRegex())) {
            password.setError("Password must have at least 1 special character")
            return false
        }
        return true
    }
    private fun validateName(name: EditText, label: TextView): Boolean {
        if (name.length() == 0) {
            label.text = "Required"
            label.visibility = View.VISIBLE
            return false
        } else {
            label.visibility = View.INVISIBLE
            return true
        }
    }
    private fun validateEmail(email: EditText, label: TextView): Boolean {
        if (email.length() == 0) {
            label.text = "Required"
            label.visibility = View.VISIBLE
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            label.text = "Email format not valid"
            label.visibility = View.VISIBLE
            return false
        } else {
            label.visibility = View.INVISIBLE
            return true
        }
    }
    private fun validatePassword(
        password: EditText,
        labelRequired: TextView,
        labelSpecial: TextView,
        labelLength: TextView,
        length: Int
    ): Boolean {
        if (password.length() == 0) {
            labelRequired.text = "Required"
            labelRequired.visibility = View.VISIBLE
            labelRequired.setTextColor(Color.parseColor("#808080"))
            return false
        } else {
            labelRequired.text = "Required"
            labelRequired.visibility = View.VISIBLE
            labelRequired.setTextColor(Color.parseColor("#f77814"))
        }

        if (length < 8) {
            labelLength.visibility = View.VISIBLE
            labelLength.setTextColor(Color.parseColor("#808080"))
            if (!password.text.matches(".*[^A-Za-z0-9].*".toRegex())) {
                labelSpecial.visibility = View.VISIBLE
                labelSpecial.setTextColor(Color.parseColor("#808080"))
                return false
            } else {
                labelSpecial.visibility = View.VISIBLE
                labelSpecial.setTextColor(Color.parseColor("#f77814"))
            }
            return false
        } else {
            labelLength.visibility = View.VISIBLE
            labelLength.setTextColor(Color.parseColor("#f77814"))

            if (!password.text.matches(".*[^A-Za-z0-9].*".toRegex())) {
                labelSpecial.visibility = View.VISIBLE
                labelSpecial.setTextColor(Color.parseColor("#808080"))
                return false
            } else {
                labelSpecial.visibility = View.VISIBLE
                labelSpecial.setTextColor(Color.parseColor("#f77814"))
            }
        }
        return true
    }
    private fun validateAllInputs(name: Boolean, email: Boolean, password: Boolean): Boolean {
        return name && email && password
    }



    private fun signup(name: String, email: String, password: String) {
        Toast.makeText(this.applicationContext, "Please wait...", Toast.LENGTH_SHORT).show()

        val user = User()
        user.userImage = ""
        user.name = name
        user.email = email
        user.password = password
        user.surname = ""
        user.cars = ArrayList<Car>()
        user.lastLogin = ""
        user.rentedCars = 0
        user.username = ""


        val apiService = RestAPIService()

        apiService.createuser(user) {
            if (it?.Id != null) {

                Toast.makeText(this.applicationContext, "Singing up completed", Toast.LENGTH_SHORT).show()


                val shared: SharedPreferences =
                    this.applicationContext.getSharedPreferences("Login", Context.MODE_PRIVATE)
                val edit = shared.edit()
                edit.putString("email", user.email)
                edit.putString("password",user.password)
                edit.putString("userFullName", user.name+user.surname)
                edit.putString("rentedCars", user.cars!!.size.toString())
                edit.commit()


                val intent = Intent(this.applicationContext, MainActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(
                    this.applicationContext,
                    "Failed Singing up, try again or check validations",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


}