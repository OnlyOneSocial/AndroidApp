package com.onlyone.androidAPP.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.safetynet.SafetyNet
import com.onlyone.androidAPP.ui.theme.BackgroundAppColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStore = StoreMain(context)
    val viewModelLogin: LoginViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundAppColor)
            .padding(start = 90.dp, end = 90.dp)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "Авторизация", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        TextField(modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth(),
            value = login,
            placeholder = {
                Text(
                    text = "Введите логин"
                )
            }, onValueChange = { login = it })
        TextField(modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            placeholder = {
                Text(
                    text = "Введите пароль"
                )
            }, onValueChange = { password = it })

        Button(modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp), onClick = {
            SafetyNet.getClient(context)
                .verifyWithRecaptcha("6LepD6geAAAAAGr_UrhHRv4gpdu1ATyX02r-seio")
                .addOnSuccessListener { response ->
                    if (response.tokenResult?.isNotEmpty() == true) {
                        coroutineScope.launch {
                            viewModelLogin.loginUser(
                                username = login,
                                password = password,
                                captcha = response.tokenResult!!
                            ).collect { auth ->
                                viewModelLogin.setToken(auth.jwt).collect{
                                    dataStore.saveToken(auth.jwt)
                                    navController.navigate("Feed") {
                                        navController.graph.startDestinationRoute?.let { screen_route ->
                                            popUpTo(screen_route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                            }
                        }
                    }
                }

        }) { Text(text = "login") }
    }
}
