package com.example.geocarrenting.UserPages

import android.content.Intent
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.geocarrenting.API.model.Car

import com.example.geocarrenting.API.model.User
import com.example.geocarrenting.CarInfo
import com.example.geocarrenting.R
import com.example.geocarrenting.databinding.ActivityUserInfoBinding
import com.example.geocarrenting.databinding.SmallCarCardBinding

class UserAdapter(val userCars: List<Car>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SmallCarCardBinding.bind(view)

        fun bind(cars: Car) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            binding.carName.text = cars.make + " " + cars.model
            binding.carPrice.text = cars.carPrice.toString() + "€/day"
            Glide.with(itemView)
                .load(cars.carImage)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(400)) //Optional
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.car_default))
                .into(binding.carImage)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(layout.inflate(R.layout.small_car_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = userCars[position]
        holder.bind(car)
        holder.itemView.findViewById<CardView>(R.id.cardId).setOnClickListener {
            val intent = Intent(it.context, CarInfo::class.java)
            intent.putExtra("carImage", car.carImage)
            intent.putExtra("carName", car.make + " " + car.model)
            intent.putExtra("carPrice", car.carPrice.toString() + "€/day")
            intent.putExtra("carSeats", car.numberOfSeats)
            intent.putExtra("carTransmission", car.transmission)
            intent.putExtra("carHP", car.engineHp.toString() + "hp")
            intent.putExtra("carEngineType", car.engineType)
            intent.putExtra("carRating", car.carRating)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userCars.size
    }

}