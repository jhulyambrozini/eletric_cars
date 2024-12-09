package com.example.eletricscarts.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletricscarts.R
import com.example.eletricscarts.data.CarFactory
import com.example.eletricscarts.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarFragment : Fragment() {
    lateinit var btnRedirect: FloatingActionButton
    lateinit var listCars: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupListeners()
        setupList()
    }

    fun setupListeners() {
        btnRedirect.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    fun setupList() {
        val adapter = CarAdapter(CarFactory.list)
        listCars.adapter = adapter
    }

    fun setupView(view: View) {
        btnRedirect = view.findViewById(R.id.fav_calcular)
        listCars = view.findViewById(R.id.rv_list_cars)
    }
}