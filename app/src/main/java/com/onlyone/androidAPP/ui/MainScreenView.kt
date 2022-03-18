package com.onlyone.androidAPP.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.onlyone.androidAPP.R
import com.onlyone.androidAPP.api.user.get_users.response.User
import com.onlyone.androidAPP.ui.theme.ContentBoxColor

@Composable
fun MainScreenView(user: User?) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController, user)
    }
}


@Preview(showBackground = false)
@Composable
fun DefaultPreview() {

        //HomeScreen()

}

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Feed : BottomNavItem("Feed", R.drawable.ic_home, "feed")
    object Dialogs : BottomNavItem("Dialogs", R.drawable.ic_home, "dialogs")
    object Profile : BottomNavItem("Profile", R.drawable.no_avatar, "myProfile")
}


@Composable
fun NetworkScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ContentBoxColor)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "My Network Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}


