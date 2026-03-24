package com.example.fenixsport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.fenixsport.ui.screens.admin.AdminScreen
import com.example.fenixsport.ui.screens.cart.CartScreen
import com.example.fenixsport.ui.screens.home.HomeScreen
import com.example.fenixsport.ui.screens.login.LoginScreen
import com.example.fenixsport.ui.screens.product.ProductDetailScreen
import com.example.fenixsport.ui.screens.register.RegisterScreen
import com.example.fenixsport.viewmodel.AuthViewModel
import com.example.fenixsport.viewmodel.MainViewModel

private sealed interface Screen {
    data object Login : Screen
    data object Register : Screen
    data object Home : Screen
    data class Detail(val id: Int) : Screen
    data object Cart : Screen
    data object Admin : Screen
}

@Composable
fun AppNavigation() {
    val authViewModel = remember { AuthViewModel() }
    val mainViewModel = remember { MainViewModel() }

    val authState by authViewModel.uiState.collectAsState()
    val mainState by mainViewModel.uiState.collectAsState()

    val screenStack = remember {
        mutableStateListOf<Screen>(
            if (authState.isLoggedIn) Screen.Home else Screen.Login
        )
    }

    fun navigateTo(screen: Screen) {
        screenStack.add(screen)
    }

    fun goBack() {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.lastIndex)
        }
    }

    fun resetTo(screen: Screen) {
        screenStack.clear()
        screenStack.add(screen)
    }

    when (val currentScreen = screenStack.last()) {
        Screen.Login -> {
            LoginScreen(
                authUiState = authState,
                onLogin = authViewModel::login,
                onGoToRegister = { navigateTo(Screen.Register) },
                onLoginSuccess = { resetTo(Screen.Home) },
                onClearMessages = authViewModel::clearMessages
            )
        }

        Screen.Register -> {
            RegisterScreen(
                authUiState = authState,
                onRegister = authViewModel::register,
                onGoToLogin = { goBack() },
                onClearMessages = authViewModel::clearMessages
            )
        }

        Screen.Home -> {
            HomeScreen(
                products = mainState.products,
                isAdmin = authState.isAdmin,
                onProductClick = { id -> navigateTo(Screen.Detail(id)) },
                onAddToCart = { product -> mainViewModel.addToCart(product) },
                onCartClick = { navigateTo(Screen.Cart) },
                onAdminClick = { navigateTo(Screen.Admin) },
                onLogout = {
                    authViewModel.logout()
                    resetTo(Screen.Login)
                }
            )
        }

        is Screen.Detail -> {
            ProductDetailScreen(
                product = mainViewModel.getProductById(currentScreen.id),
                onBack = { goBack() },
                onAddToCart = { product -> mainViewModel.addToCart(product) },
                onGoToCart = { navigateTo(Screen.Cart) }
            )
        }

        Screen.Cart -> {
            CartScreen(
                cartItems = mainState.cart,
                total = mainState.total,
                onBack = { goBack() },
                onRemoveItem = { id -> mainViewModel.removeFromCart(id) },
                onCheckout = { mainViewModel.checkout() }
            )
        }

        Screen.Admin -> {
            if (!authState.isAdmin) {
                LaunchedEffect(Unit) {
                    resetTo(Screen.Home)
                }
            } else {
                AdminScreen(
                    products = mainState.products,
                    message = mainState.adminMessage,
                    onBack = { goBack() },
                    onCreateProduct = mainViewModel::createProduct,
                    onUpdateProduct = mainViewModel::updateProduct,
                    onDeleteProduct = mainViewModel::deleteProduct,
                    onClearMessage = mainViewModel::clearAdminMessage
                )
            }
        }
    }
}