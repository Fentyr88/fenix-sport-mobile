package com.example.fenixsport.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fenixsport.R
import com.example.fenixsport.data.model.Product
import com.example.fenixsport.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val products: List<Product> = emptyList(),
    val cart: List<Product> = emptyList(),
    val adminMessage: String? = null
) {
    val total: Double = cart.sumOf { it.price }
}

class MainViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MainUiState(
            products = repository.getProducts(),
            cart = repository.getCartItems()
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun getProductById(productId: Int): Product? = repository.getProductById(productId)

    fun addToCart(product: Product) {
        repository.addToCart(product)
        refreshCart()
    }

    fun removeFromCart(productId: Int) {
        repository.removeFromCart(productId)
        refreshCart()
    }

    fun checkout() {
        repository.clearCart()
        _uiState.update { it.copy(cart = emptyList(), adminMessage = "Compra finalizada con exito.") }
    }

    fun clearAdminMessage() {
        _uiState.update { it.copy(adminMessage = null) }
    }

    fun createProduct(name: String, description: String, priceText: String): Boolean {
        val parsedPrice = priceText.toDoubleOrNull()
        if (name.isBlank() || description.isBlank() || parsedPrice == null || parsedPrice <= 0.0) {
            _uiState.update { it.copy(adminMessage = "Datos invalidos para crear producto.") }
            return false
        }

        val nextId = (repository.getProducts().maxOfOrNull { it.id } ?: 0) + 1
        repository.createProduct(
            Product(
                id = nextId,
                name = name.trim(),
                description = description.trim(),
                price = parsedPrice,
                image = R.drawable.fenix_logo,
                category = "General"
            )
        )

        refreshProducts(message = "Producto creado.")
        return true
    }

    fun updateProduct(productId: Int, name: String, description: String, priceText: String): Boolean {
        val original = repository.getProductById(productId) ?: return false
        val parsedPrice = priceText.toDoubleOrNull()

        if (name.isBlank() || description.isBlank() || parsedPrice == null || parsedPrice <= 0.0) {
            _uiState.update { it.copy(adminMessage = "Datos invalidos para editar producto.") }
            return false
        }

        repository.updateProduct(
            original.copy(
                name = name.trim(),
                description = description.trim(),
                price = parsedPrice
            )
        )

        refreshProducts(message = "Producto actualizado.")
        return true
    }

    fun deleteProduct(productId: Int) {
        repository.deleteProduct(productId)
        _uiState.update {
            it.copy(
                products = repository.getProducts(),
                cart = repository.getCartItems(),
                adminMessage = "Producto eliminado."
            )
        }
    }

    private fun refreshProducts(message: String? = null) {
        _uiState.update {
            it.copy(
                products = repository.getProducts(),
                adminMessage = message
            )
        }
    }

    private fun refreshCart() {
        _uiState.update { it.copy(cart = repository.getCartItems()) }
    }
}

