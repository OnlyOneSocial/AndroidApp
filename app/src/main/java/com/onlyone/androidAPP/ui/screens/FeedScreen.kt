package com.onlyone.androidAPP.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.onlyone.androidAPP.R
import com.onlyone.androidAPP.api.user.get_users.response.PostsDataItem
import com.onlyone.androidAPP.ui.theme.BackgroundAppColor
import com.onlyone.androidAPP.ui.theme.ContentBoxColor
import kotlinx.coroutines.launch

@Composable
fun UserImage(avatar: String, userid: Int, size: Dp) {
    if (avatar != "") {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cdn.only-one.su/public/clients/${userid}/100-${avatar}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .width(size)
                .height(size)
        )
    } else {
        Image(
            painter = painterResource(R.drawable.no_avatar),
            modifier = Modifier
                .clip(CircleShape)
                .width(size)
                .height(size),
            contentDescription = null,
        )
    }
}

@Composable
fun Post(post: PostsDataItem, navController: NavHostController) {


    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(ContentBoxColor)
    ) {
        Column {
            Row(modifier = Modifier.clickable(onClick = {
                navController.navigate("profile/${post.author}")
            })) {
                UserImage(post.author_avatar, post.author, 35.dp)
                Column(modifier = Modifier.padding(start = 4.dp)) {
                    Row {
                        Text(text = post.author_username, color = Color(0xFFC8D7DE))
                    }
                    Text(text = post.time, fontSize = 12.sp, color = Color(0xFF9C9C9C))
                }
            }

            Text(text = post.text, color = Color(0xFFFFFFFF))

            Row(modifier = Modifier.padding(top = 4.dp,bottom = 4.dp)) {
                if (post.likes != null) Text(
                    text = post.likes.size.toString(),
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.size(14.dp)
                )
                else Text(text = "0", color = Color(0xFFFFFFFF), modifier = Modifier.size(14.dp))
                Icon(
                    painterResource(id = R.drawable.ic_like),
                    modifier = Modifier.size(14.dp),
                    contentDescription = "Like"
                )
                Text(
                    text = "0",
                    modifier = Modifier
                        .size(14.dp)
                        .padding(start = 4.dp),
                    color = Color(0xFFFFFFFF)
                )
                Icon(
                    painterResource(id = R.drawable.ic_dislike),
                    modifier = Modifier.size(14.dp),
                    contentDescription = "Dislike"
                )
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavHostController) {
    //lateinit var posts : List<PostsDataItem>
    val (posts, setPosts) = remember { mutableStateOf<List<PostsDataItem>?>(null) }
    val viewModelLogin: LoginViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundAppColor)
    ) {

        coroutineScope.launch {
            viewModelLogin.getFeed().collect {
                setPosts(it)
            }
        }

        if (posts != null) {
            LazyColumn {
                items(posts.size) { index ->
                    Post(posts[index], navController)
                }
            }
        }
    }
}
