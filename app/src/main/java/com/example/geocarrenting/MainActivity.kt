package com.example.geocarrenting

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geocarrenting.API.APIService
import com.example.geocarrenting.API.Adapter
import com.example.geocarrenting.API.model.Car
import com.example.geocarrenting.UserPages.UserInfo
import com.example.geocarrenting.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private lateinit var binding: ActivityMainBinding
    private var carsInfo = mutableListOf<Car>()
    var models = arrayListOf<String>()

    companion object {
        const val BASE_URL: String = "https://x46vog.deta.dev/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getCars(20)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getCarsByModel(binding.searchBar.query.toString())
                getCars(2050)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (binding.searchBar.query.toString() == "") {

                }
                return false
            }
        })
        binding.userImage.setOnClickListener {
            startActivity(Intent(this.applicationContext, UserInfo::class.java))
        }

        //SPINNERS
        val spinList = models
        val spinAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, spinList)
        binding.filterSpin1.adapter = spinAdapter
        binding.filterSpin1.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }





    }

    private fun initRecyclerView() {
        adapter = Adapter(carsInfo)
        binding.rv1.layoutManager = LinearLayoutManager(this)
        binding.rv1.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun getCars(carNumber: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val getCars = getRetrofit().create(APIService::class.java).getCars("cars/$carNumber")
            val cars = getCars.body()


            for (x in cars!!) {
                models.add(x.model.toString())
            }

            runOnUiThread {
                initCharacter(cars)
            }
        }
    }

    private fun getCarsByModel(carModel: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val getCars =
                getRetrofit().create(APIService::class.java).getCarsByModel("cars/m/$carModel")
            val cars = getCars.body()
            runOnUiThread {
                initCharacter(cars)
            }
        }
    }

    private fun initCharacter(cars: List<Car>?) {
        carsInfo.removeFirstOrNull()
        for (car in cars!!) {
            carsInfo.add(car)
        }
        initRecyclerView()
    }

    private fun initCharacter(car: Car?) {
        if (car != null) {
            carsInfo = mutableListOf<Car>()
            carsInfo.add(car)
        }
        initRecyclerView()
    }

    private fun defaultToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { super.onBackPressed() }
    }
}