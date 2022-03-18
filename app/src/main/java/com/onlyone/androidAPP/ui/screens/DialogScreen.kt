package com.onlyone.androidAPP.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.onlyone.androidAPP.R
import com.onlyone.androidAPP.api.user.get_users.response.Dialog
import com.onlyone.androidAPP.api.user.get_users.response.User
import com.onlyone.androidAPP.ui.theme.BackgroundAppColor
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DialogScreen(navController: NavHostController, user: User) {
    //lateinit var posts : List<PostsDataItem>
    val (posts, setPosts) = remember { mutableStateOf<List<Dialog>?>(null) }
    val viewModelLogin: LoginViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundAppColor)
    ) {

        coroutineScope.launch {
            viewModelLogin.getDialogs().collect {
                setPosts(it)
            }
        }

        if (posts != null) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(posts) { post: Dialog ->
                    var dialogWith = post.userid
                    if (dialogWith == user.id) dialogWith = post.sendto

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clickable(onClick = {
                            navController.navigate("messages/${dialogWith}")
                        })
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            UserImage(post.avatar, dialogWith, 35.dp)
                            Text(text = post.username, modifier = Modifier.padding(start = 8.dp))
                        }
                        Row {
                            Text(text = post.text, modifier = Modifier
                                .weight(3f)
                                .padding(start = 4.dp)
                                .wrapContentWidth(Alignment.Start))
                            Divider(
                                color = Color.Black,
                                modifier = Modifier.fillMaxHeight().width(1.dp)
                            )
                            Text(text = post.time, textAlign = TextAlign.Right,modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                                .wrapContentWidth(Alignment.End),)
                        }
                    }
                }
            }
        }
    }
}
