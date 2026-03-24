package com.example.fenixsport.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fenixsport.R
import com.example.fenixsport.ui.components.FenixButton
import com.example.fenixsport.viewmodel.AuthUiState

@Composable
fun LoginScreen(
    authUiState: AuthUiState,
    onLogin: (String, String) -> Boolean,
    onGoToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onClearMessages: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authUiState.isLoggedIn) {
        if (authUiState.isLoggedIn) onLoginSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.hero_bg),
                contentDescription = "Hero",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.fenix_logo),
                contentDescription = "Fenix Logo",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(88.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = "Fenix Sport", style = MaterialTheme.typography.headlineLarge)
        Text(
            text = "Inicia sesion para continuar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                onClearMessages()
            },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                onClearMessages()
            },
            label = { Text("Contrasena") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        authUiState.loginError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        FenixButton(text = "Ingresar", onClick = { onLogin(email, password) })

        TextButton(onClick = onGoToRegister, modifier = Modifier.padding(top = 8.dp)) {
            Text("Crear cuenta")
        }
    }
}