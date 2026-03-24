package com.example.fenixsport.repository

import com.example.fenixsport.data.local.FakeData
import com.example.fenixsport.data.model.Product

class ProductRepository {
    private val products = FakeData.products.toMutableList()
    private val cart = mutableListOf<Product>()

    fun getProducts(): List<Product> = products.toList()

    fun getProductById(id: Int): Product? = products.firstOrNull { it.id == id }

    fun getCartItems(): List<Product> = cart.toList()

    fun addToCart(product: Product) {
        cart.add(product)
    }

    fun removeFromCart(productId: Int) {
        val item = cart.firstOrNull { it.id == productId } ?: return
        cart.remove(item)
    }

    fun clearCart() {
        cart.clear()
    }

    fun createProduct(product: Product) {
        products.add(product)
    }

    fun updateProduct(updatedProduct: Product) {
        val index = products.indexOfFirst { it.id == updatedProduct.id }
        if (index != -1) {
            products[index] = updatedProduct
        }
    }

    fun deleteProduct(productId: Int) {
        products.removeAll { it.id == productId }
        cart.removeAll { it.id == productId }
    }
}