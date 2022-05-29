package com.example.geocarrenting.UserPages

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geocarrenting.API.APIService
import com.example.geocarrenting.API.Adapter
import com.example.geocarrenting.API.model.User
import com.example.geocarrenting.ForbiddenPassword
import com.example.geocarrenting.MainActivity
import com.example.geocarrenting.R
import com.example.geocarrenting.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userInfo: User

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        val shared: SharedPreferences = applicationContext.getSharedPreferences("Login", Context.MODE_PRIVATE)

        val email = shared.getString("email", "") // Intenta coger el valor email
        val password = shared.getString("password", "")



        binding.buttonSignin.setOnClickListener{
            if(email != null && password != null){
                if(getUserLogin(email, password) != null){
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }

        }

        binding.buttonSignup.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

//    private fun getUser(email: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val getUser = getRetrofit().create(APIService::class.java).getUserByEmail("$email")
//            val user = getUser.body()
//            runOnUiThread {
//                initCharacter(user)
//            }
//        }
//    }
    private fun getUserLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val getUser = getRetrofit().create(APIService::class.java).getUserByEmail("$email/$password")
            val user = getUser.body()
            runOnUiThread {
                initCharacter(user)
            }
        }
    }

    private fun initCharacter(user: User?) {
        if (user != null) {
            userInfo = user
        }
//        initRecyclerView()
    }
//    private fun initRecyclerView() {
////        adapter = Adapter(userInfo)
////        binding.rv1.layoutManager = LinearLayoutManager(this)
////        binding.rv1.adapter = adapter
//    }
}