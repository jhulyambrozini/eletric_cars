package com.example.eletricscarts.data

import com.example.eletricscarts.domain.Car

object CarFactory {

    var list = listOf<Car>(
        Car(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "300 kWh",
            potencia = "200cv",
            recarga = "30 min",
            urlFoto = "www.google.com.br"
        ),
        Car(
            id = 2,
            preco = "R$ 200.000,00",
            bateria = "200 kWh",
            potencia = "150cv",
            recarga = "40 min",
            urlFoto = "www.google.com.br"
        )
    )
}