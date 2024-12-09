package com.example.eletricscarts.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eletricscarts.R

class CalcularAutonomiaActivity : AppCompatActivity() {
    lateinit var inputPrice: EditText
    lateinit var inputKmPecorrido: EditText
    lateinit var btnCalculate: Button
    lateinit var btnClose: ImageView
    lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListeners()

    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            calculateConsumer()
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    fun setupView() {
        inputPrice = findViewById(R.id.et_preco)
        inputKmPecorrido = findViewById(R.id.et_km_pecorrido)
        btnCalculate = findViewById(R.id.btn_calcular)
        btnClose = findViewById(R.id.iv_close)
        result = findViewById(R.id.tv_resultado_calculo)
    }

    fun calculateConsumer() {
        val price = inputPrice.text.toString().toFloat()
        val kmPecorrido = inputKmPecorrido.text.toString().toFloat()
        val consume = price / kmPecorrido
        result.text = consume.toString()

        Log.d("Resultado calculo price", result.toString())
    }
}