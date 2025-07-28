package com.example.goevents.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val isRegistering by viewModel.isRegistering.collectAsState()
    val registerError by viewModel.registerError.collectAsState()
    val registerSuccess by viewModel.registerSuccess.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register your organization", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (registerError != null) viewModel.setRegisterError("")
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                if (registerError != null) viewModel.setRegisterError("")
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (registerError != null) viewModel.setRegisterError("")
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                if (registerError != null) viewModel.setRegisterError("")
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )

        if (!registerError.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = registerError ?: "", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (password != confirmPassword) {
                    viewModel.setRegisterError("Passwords do not match")
                } else if (email.isBlank() || password.isBlank()) {
                    viewModel.setRegisterError("Email and password must not be empty")
                } else {
                    val trimmedEmail = email.trim()
                    viewModel.register(trimmedEmail, name, password)
                }
            },
            enabled = !isRegistering && registerError.isNullOrEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isRegistering) "Registering..." else "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Already have an account? Login",
            modifier = Modifier.clickable {
                navController.navigate("login")
            },
            color = MaterialTheme.colorScheme.primary
        )
    }

    LaunchedEffect(registerSuccess) {
        if (registerSuccess) {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }
    }
}