package com.example.ridesharinghc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.example.ridesharinghc.ui.theme.LogoBlue

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RideSharingHCTheme {
                LoginScreen(onBackClick = { navigateBackToLoginRegisterActivity() })
            }
        }
    }

    // Function to navigate back to LoginRegisterActivity
    private fun navigateBackToLoginRegisterActivity() {
        finish()  // Fecha a atividade atual e volta para a anterior
    }
}

@Composable
fun LoginScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Obtendo o contexto para navegação entre atividades
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        // Ícone de voltar no canto superior esquerdo
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(30.dp)
            )
        }

        Box(
            modifier = Modifier
                .width(400.dp)
                .height(950.dp)
                .padding(top = 56.dp)
                .padding(bottom = 15.dp)
                .padding(start = 26.dp)
                .background(SoftBlue)
                .border(8.dp, LogoBlue)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Login", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de entrada de email
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de entrada de senha
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botão de login
                Button(
                    onClick = {
                        // Validação simulada de login e navegação para HomeScreenActivity
                        val intent = Intent(context, HomeScreenActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        }
    }
}
