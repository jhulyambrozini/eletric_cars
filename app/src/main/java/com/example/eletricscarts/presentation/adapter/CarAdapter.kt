package com.example.eletricscarts.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricscarts.R
import com.example.eletricscarts.domain.Car

 class CarAdapter(private val carros: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val preco: TextView
        val bateria: TextView
        val recarga: TextView
        val potencia: TextView

        init {
            preco = view.findViewById(R.id.tv_preco_value)
            bateria = view.findViewById(R.id.tv_bateria_value)
            potencia = view.findViewById(R.id.tv_potencia_value)
            recarga = view.findViewById(R.id.tv_recarga_value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return carros.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text = carros[position].recarga
    }
}

