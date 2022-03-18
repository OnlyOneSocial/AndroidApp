package com.onlyone.androidAPP.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.onlyone.androidAPP.R
import com.onlyone.androidAPP.api.user.get_users.response.DialogMessage
import com.onlyone.androidAPP.api.user.get_users.response.User
import com.onlyone.androidAPP.ui.theme.BackgroundAppColor
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MessagesListScreen(navController: NavHostController, user: User, dialogWith: Int) {
    //lateinit var posts : List<PostsDataItem>
    val (messages, setMessages) = remember { mutableStateOf<List<DialogMessage>?>(null) }
    var enterMessage by rememberSaveable { mutableStateOf("") }
    val viewModelLogin: LoginViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundAppColor)
    ) {

        coroutineScope.launch {
            viewModelLogin.getMessages(dialogWith).collect {
                setMessages(it)
                listState.animateScrollToItem(index = it.size)
            }
        }

        if (messages != null) {
            LazyColumn (state = listState){
                items(messages) { message: DialogMessage ->


                    Column {
                        Row(modifier = Modifier.clickable(onClick = {
                            navController.navigate("profile/${dialogWith}")
                        })) {
                            UserImage(message.avatar, message.userid, 35.dp)
                            Text(text = message.username, modifier = Modifier.padding(start = 4.dp))
                        }
                        Row(modifier = Modifier.clickable(onClick = {
                            navController.navigate("profile/${dialogWith}")
                        })) {
                            Text(text = message.text)
                            Text(text = message.time, modifier = Modifier.padding(start = 4.dp))
                        }
                    }


                }

            }
            Row(modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(start = 4.dp, end = 4.dp, top = 8.dp)) {
                TextField(
                    visualTransformation = PasswordVisualTransformation(),
                    value = enterMessage,
                    placeholder = {
                        Text(
                            text = "напишите что нибудь"
                        )
                    }, onValueChange = { enterMessage = it })
                Button(onClick = { /*TODO*/ })
                { Text(text = "Send") }
            }
        }
    }
}
