package com.example.fenixsport.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fenixsport.R
import com.example.fenixsport.data.model.Product
import com.example.fenixsport.ui.components.ProductCard
import com.example.fenixsport.ui.components.TopBar

@Composable
fun HomeScreen(
    products: List<Product>,
    isAdmin: Boolean,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Product) -> Unit,
    onCartClick: () -> Unit,
    onAdminClick: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = "Fenix Sport",
                actions = buildList {
                    add("Carrito" to onCartClick)
                    if (isAdmin) add("Admin" to onAdminClick)
                    add("Salir" to onLogout)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.hero_bg),
                    contentDescription = "Hero",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            itemsIndexed(products, key = { _, item -> item.id }) { index, product ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 450, delayMillis = index * 80)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(durationMillis = 450, delayMillis = index * 80)
                        )
                ) {
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product.id) },
                        onActionClick = { onAddToCart(product) },
                        actionLabel = "Agregar"
                    )
                }
            }
        }
    }
}