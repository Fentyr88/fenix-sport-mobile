package com.example.fenixsport.ui.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fenixsport.data.model.Product
import com.example.fenixsport.ui.components.FenixButton
import com.example.fenixsport.ui.components.TopBar
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductDetailScreen(
    product: Product?,
    onBack: () -> Unit,
    onAddToCart: (Product) -> Unit,
    onGoToCart: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(title = "Detalle", onBack = onBack, actions = listOf("Carrito" to onGoToCart)) }
    ) { innerPadding ->
        if (product == null) {
            Text(
                text = "Producto no encontrado",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )

            Text(text = product.name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = formatPrice(product.price),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
            )

            FenixButton(
                text = "Agregar al carrito",
                onClick = { onAddToCart(product) },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun formatPrice(price: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("es", "CO")).format(price)
}