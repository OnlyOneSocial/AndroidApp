package com.onlyone.androidAPP.ui

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.onlyone.androidAPP.R
import com.onlyone.androidAPP.api.user.get_users.response.GetUser
import com.onlyone.androidAPP.api.user.get_users.response.PostsDataItem
import kotlinx.coroutines.launch

import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.onlyone.androidAPP.ui.theme.BackgroundAppColor
import com.onlyone.androidAPP.ui.theme.ContentBoxColor


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.coloredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = composed {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha= 0f).toArgb()

    this.drawBehind {

        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent

            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}


fun getOnline(online: Long, context: Context): String {
    return when (val num = System.currentTimeMillis() / 1000 - online) {
        in -60 * 3..60 * 3 -> "Онлайн"
        in 60 * 3..60 * 60 -> "${num / 60} минут назад"
        in 60 * 60..60 * 60 * 24 -> "${num / 60 / 60} час назад"
        in 60 * 60 * 24..60 * 60 * 24 * 31 -> "${num / 60 / 60 / 24} день назад"
        else -> {
            DateUtils.formatDateTime(
                context,
                online * 1000,
                DateUtils.FORMAT_SHOW_DATE
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Profile(userid: Int, navController: NavHostController) {
    val (user, setUser) = remember { mutableStateOf<GetUser?>(null) }
    val (posts, setPosts) = remember { mutableStateOf<List<PostsDataItem>?>(null) }
    var enterMessage by rememberSaveable { mutableStateOf("") }
    val viewModelLogin: LoginViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundAppColor)
        //.wrapContentSize(Alignment.Center)
    ) {

        coroutineScope.launch {
            viewModelLogin.getUser(userid).collect {
                setUser(it)
            }
        }
        coroutineScope.launch {
            viewModelLogin.getPosts(userid).collect {
                setPosts(it)
            }
        }

        user?.user?.let {
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 4.dp)
                .fillMaxWidth()
                //.size(400.dp,110.dp)
                .clip(RoundedCornerShape(7.dp))
                .coloredShadow(ContentBoxColor, shadowRadius = 4.dp, borderRadius = 4.dp)
                .background(ContentBoxColor)){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        UserImage(it.avatar, it.id, 50.dp)
                        Column(modifier = Modifier.padding(start = 4.dp)) {
                            Row {
                                Text(text = it.username, color = Color.White)
                                Text(
                                    text = getOnline(it.online, context),
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                            Text(text = it.status, color = Color.White)
                        }
                    }
                    Text(text = it.bio, color = Color.White)
                    IconButton( modifier= Modifier,onClick = {
                        navController.navigate("messages/${userid}")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_message),
                            contentDescription = "Send Msg"
                        )
                    }

                }
            }
        }
        if (user?.user?.me == true){
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(start = 4.dp, end = 4.dp, top = 8.dp)) {
                TextField(
                    modifier= Modifier
                        .background(colorResource(id = R.color.white))
                        .weight(9f),
                    value = enterMessage,
                    placeholder = {
                        Text(
                            text = "напишите что нибудь"
                        )
                    }, onValueChange = { enterMessage = it })
                IconButton( modifier= Modifier.weight(1f),onClick = {
                    coroutineScope.launch {
                        viewModelLogin.sendPost(message = enterMessage, answer = "").collect {
                            enterMessage=""
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send"
                    )
                }
            }

        }

        posts?.let {
            LazyColumn {
                items(posts) { item: PostsDataItem ->
                    Post(item, navController)
                }
            }
        }
    }
}