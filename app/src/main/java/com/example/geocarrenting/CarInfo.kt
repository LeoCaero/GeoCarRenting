package com.example.geocarrenting

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.geocarrenting.API.APIService
import com.example.geocarrenting.MainActivity.Companion.BASE_URL
import com.example.geocarrenting.databinding.ActivityCarInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CarInfo : AppCompatActivity() {
    private lateinit var binding: ActivityCarInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        binding.carName.text = intent.extras?.getString("carName")
        binding.carPrice.text = intent.extras?.getString("carPrice")
        binding.carSeats.text = intent.extras?.getInt("carSeats").toString()
        binding.avgRanking.text = String.format("%.1f", intent.extras?.getFloat("carRating"))
        binding.carHP.text = intent.extras?.getString("carHP")
        binding.carEngineType.text = intent.extras?.getString("carEngineType")
        if (intent.extras?.getString("carTransmission")
                .equals("") || intent.extras?.getString("carTransmission") == null
        ) {
            binding.carTransmission.text = "Manual"
        } else {
            binding.carTransmission.text = intent.extras?.getString("carTransmission")
        }

        Glide.with(this.applicationContext)
            .load(intent.extras?.getString("carImage"))
            .transition(DrawableTransitionOptions.withCrossFade(400)) //Optional
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.car_default))
            .into(binding.carImage)

        binding.rankingStars.rating = intent.extras?.getFloat("carRating")!!

        val shared: SharedPreferences =
            applicationContext.getSharedPreferences("Login", Context.MODE_PRIVATE)

        val email = shared.getString("email", "")
        Toast.makeText(this, email.toString(), Toast.LENGTH_LONG).show()

        binding.bookButton.setOnClickListener {
            if(intent.extras?.getString("carModel") != null){
                putCarToUser(email!!, intent.extras!!.getString("carModel")!!)
            }

        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun putCarToUser(email: String, model: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).addCarToUser(email, model)
            val carAdded = call.body()
            if (call.isSuccessful) {
                print(model)
            } else {
                //show error
            }
        }
    }

}