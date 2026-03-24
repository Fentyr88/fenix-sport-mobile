package com.example.fenixsport.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fenixsport.data.model.Product
import com.example.fenixsport.ui.components.FenixButton
import com.example.fenixsport.ui.components.TopBar

@Composable
fun AdminScreen(
    products: List<Product>,
    message: String?,
    onBack: () -> Unit,
    onCreateProduct: (String, String, String) -> Boolean,
    onUpdateProduct: (Int, String, String, String) -> Boolean,
    onDeleteProduct: (Int) -> Unit,
    onClearMessage: () -> Unit
) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    fun loadProduct(product: Product?) {
        selectedProduct = product
        name = product?.name.orEmpty()
        description = product?.description.orEmpty()
        price = product?.price?.toString().orEmpty()
        onClearMessage()
    }

    Scaffold(
        topBar = { TopBar(title = "Panel Admin", onBack = onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = if (selectedProduct == null) "Crear producto" else "Editar producto #${selectedProduct?.id}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            onClearMessage()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nombre") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            onClearMessage()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Descripcion") }
                    )

                    OutlinedTextField(
                        value = price,
                        onValueChange = {
                            price = it
                            onClearMessage()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Precio") },
                        singleLine = true
                    )

                    FenixButton(
                        text = if (selectedProduct == null) "Crear" else "Guardar cambios",
                        onClick = {
                            if (selectedProduct == null) {
                                val created = onCreateProduct(name, description, price)
                                if (created) loadProduct(null)
                            } else {
                                val updated = onUpdateProduct(selectedProduct!!.id, name, description, price)
                                if (updated) loadProduct(null)
                            }
                        }
                    )

                    if (selectedProduct != null) {
                        TextButton(
                            onClick = { loadProduct(null) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cancelar edicion")
                        }
                    }

                    if (!message.isNullOrBlank()) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(products, key = { it.id }) { product ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "$${product.price}", color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = product.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            androidx.compose.foundation.layout.Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { loadProduct(product) }) {
                                    Text("Editar")
                                }
                                TextButton(onClick = { onDeleteProduct(product.id) }) {
                                    Text("Eliminar", color = MaterialTheme.colorScheme.tertiary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

