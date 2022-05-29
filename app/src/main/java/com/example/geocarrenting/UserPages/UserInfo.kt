package com.example.geocarrenting.UserPages

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geocarrenting.API.APIService
import com.example.geocarrenting.API.Adapter
import com.example.geocarrenting.API.model.Car
import com.example.geocarrenting.API.model.User
import com.example.geocarrenting.MainActivity
import com.example.geocarrenting.R
import com.example.geocarrenting.databinding.ActivityMainBinding
import com.example.geocarrenting.databinding.ActivityUserInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserInfo : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityUserInfoBinding
    private var userInfo = User()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }


        val shared: SharedPreferences =
            applicationContext.getSharedPreferences("Login", Context.MODE_PRIVATE)

        val email = shared.getString("email", "")
        val pass = shared.getString("password", "")
        val name = shared.getString("userFullName", "")
        getUser(email!!)

//        Toast.makeText(this.applicationContext, pass.toString(), Toast.LENGTH_LONG).show()

        binding.userName.text = name
        binding.userEmail.text = email

    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun getUser(userEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val getUser =
                getRetrofit().create(APIService::class.java).getUserByEmail("users/$userEmail")
            val user = getUser.body()
            runOnUiThread {
                if (user != null) {
                    initCharacter(user)
                    binding.currentRented.text = adapter.itemCount.toString()
                }
            }
        }
    }

    private fun initCharacter(user: User) {
        userInfo = user
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = UserAdapter(userInfo.cars!!)
        binding.carsRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.carsRv.adapter = adapter
    }
}