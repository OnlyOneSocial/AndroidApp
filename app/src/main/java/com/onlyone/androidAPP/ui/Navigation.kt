package com.onlyone.androidAPP.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.onlyone.androidAPP.R
import com.onlyone.androidAPP.api.user.get_users.response.User
import com.onlyone.androidAPP.ui.theme.ContentBoxColor


@Composable
fun NavigationGraph(navController: NavHostController, user: User?) {

    var startScreen = "login"

    if (user != null) {
        startScreen = BottomNavItem.Feed.screen_route
    }

    NavHost(navController, startDestination = startScreen) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("messages/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {
            if (user != null) {
                it.arguments?.getInt("userId")?.let { it1 ->
                    MessagesListScreen(navController, user, it1)
                }
            }
        }
        composable(BottomNavItem.Feed.screen_route) {
            HomeScreen(navController)
        }
        composable(BottomNavItem.Dialogs.screen_route) {
            if (user != null) {
                DialogScreen(navController, user)
            }
        }
        composable(BottomNavItem.Profile.screen_route) {
            if (user != null) {
                Profile(user.id, navController)
            }
        }
        composable("profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {
            if (user != null) {
                it.arguments?.getInt("userId")?.let { it1 -> Profile(it1, navController) }
            }
        }
    }
}

@Composable
fun SetIconOrProfileImage(icon: Int, title: String) {
    if (title == "Profile") {
        val image = "https://cdn.only-one.su/public/clients/1/e2904c7c2d209b7e4ea11cd1d99cbcd1.png"
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .transformations(CircleCropTransformation())
                .build(),

            )

        Icon(painter = painter, contentDescription = "Profile", tint = Color.Unspecified)
    } else Icon(painterResource(id = icon), contentDescription = title)
}

@Composable
fun TopBarMenu(navController: NavController){


    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    println("is infoback ${navBackStackEntry}")

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(ContentBoxColor)
        .height(60.dp)){
        Row() {
            if(navBackStackEntry != null) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Back")
                }
            }
            Text(text = "Header")
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Feed,
        BottomNavItem.Dialogs,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != "login") BottomNavigation(
        backgroundColor = ContentBoxColor,
        contentColor = Color.White
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                icon = { SetIconOrProfileImage(item.icon, item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}