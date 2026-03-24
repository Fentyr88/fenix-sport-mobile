package com.example.fenixsport.data.local

import com.example.fenixsport.R
import com.example.fenixsport.data.model.Product

object FakeData {
    val products = listOf(
        Product(
            id = 1,
            name = "Basketball Pro",
            description = "Balon profesional para entrenamiento y competencia.",
            price = 149900.0,
            image = R.drawable.basketball,
            category = "Balones"
        ),
        Product(
            id = 2,
            name = "Boxing Gloves X",
            description = "Guantes de boxeo con gran absorcion de impacto.",
            price = 189900.0,
            image = R.drawable.boxing_gloves,
            category = "Boxeo"
        ),
        Product(
            id = 3,
            name = "Cycling Helmet Air",
            description = "Casco liviano con ventilacion avanzada para ciclismo.",
            price = 239900.0,
            image = R.drawable.cycling_helmet,
            category = "Ciclismo"
        ),
        Product(
            id = 4,
            name = "Dumbbells Set",
            description = "Mancuernas para entrenamiento funcional y fuerza.",
            price = 329900.0,
            image = R.drawable.dumbbells,
            category = "Fitness"
        ),
        Product(
            id = 5,
            name = "Running Shoes Sprint",
            description = "Zapatillas de running con amortiguacion y soporte.",
            price = 359900.0,
            image = R.drawable.running_shoes,
            category = "Calzado"
        ),
        Product(
            id = 6,
            name = "Soccer Ball Match",
            description = "Balon oficial para cancha sintetica y natural.",
            price = 129900.0,
            image = R.drawable.soccer_ball,
            category = "Futbol"
        ),
        Product(
            id = 7,
            name = "Tennis Racket Control",
            description = "Raqueta con excelente control y respuesta en golpe.",
            price = 279900.0,
            image = R.drawable.tennis_racket,
            category = "Tenis"
        ),
        Product(
            id = 8,
            name = "Yoga Mat Comfort",
            description = "Tapete antideslizante para yoga y entrenamiento en casa.",
            price = 99900.0,
            image = R.drawable.yoga_mat,
            category = "Yoga"
        )
    )
}