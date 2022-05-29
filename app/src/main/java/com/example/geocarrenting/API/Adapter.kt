package com.example.geocarrenting.API

import android.content.Intent
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.geocarrenting.API.model.Car
import com.example.geocarrenting.CarInfo
import com.example.geocarrenting.R
import com.example.geocarrenting.databinding.ActivityCarInfoBinding
import com.example.geocarrenting.databinding.CardViewBinding


class Adapter(val carsInfo: List<Car>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardViewBinding.bind(view)
        fun bind(carInfo: Car) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            binding.carNameTextView.text = carInfo.make + " " + carInfo.model //asignar datos
            binding.priceTextView.text = "From " + carInfo.carPrice + "€/day"
            binding.ratingTextView.text = String.format("%.1f",carInfo.carRating)
            Glide.with(itemView)
                .load(carInfo.carImage)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(400)) //Optional
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.car_default))
                .into(binding.carImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(layout.inflate(R.layout.card_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = carsInfo[position]
        holder.bind(car)
        holder.itemView.findViewById<ImageButton>(R.id.buttonCar).setOnClickListener{
            val intent = Intent(it.context, CarInfo::class.java)
            intent.putExtra("carImage", car.carImage)
            intent.putExtra("carModel", car.model)
            intent.putExtra("carName", car.make+" "+ car.model)
            intent.putExtra("carPrice", car.carPrice.toString()+"€/day")
            intent.putExtra("carSeats", car.numberOfSeats)
            intent.putExtra("carTransmission", car.transmission)
            intent.putExtra("carHP", car.engineHp.toString() + "hp")
            intent.putExtra("carEngineType", car.engineType)
            intent.putExtra("carRating", car.carRating)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return carsInfo.size
    }
}