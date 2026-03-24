package com.example.fenixsport.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fenixsport.data.model.Product
import com.example.fenixsport.ui.components.FenixButton
import com.example.fenixsport.ui.components.ProductCard
import com.example.fenixsport.ui.components.TopBar
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    cartItems: List<Product>,
    total: Double,
    onBack: () -> Unit,
    onRemoveItem: (Int) -> Unit,
    onCheckout: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(title = "Carrito", onBack = onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (cartItems.isEmpty()) {
                Text(
                    text = "Tu carrito esta vacio.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems, key = { "${it.id}_${it.name}" }) { product ->
                        ProductCard(
                            product = product,
                            onClick = {},
                            onActionClick = { onRemoveItem(product.id) },
                            actionLabel = "Eliminar"
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Total: ${formatPrice(total)}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    FenixButton(
                        text = "Finalizar compra",
                        onClick = onCheckout,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

private fun formatPrice(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("es", "CO")).format(value)
}
