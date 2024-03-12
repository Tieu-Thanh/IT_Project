package com.example.loginui.Screen

import android.net.Uri
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.loginui.R
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.interFontFamily


@Composable
fun UrlVideoPopUp(navController: NavHostController, url: String) {
    val videoUri by remember { mutableStateOf<Uri?>(null) }
    var videoReady by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        UrlVideoTopBackground(navController)
        Spacer(modifier = Modifier.height(35.dp))
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(400.dp)
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (videoUri != null) {
                    VideoPlayer(videoUri!!) {
                        videoReady = it
                    }
                }
                ResultVideoPlayer(Uri.parse(url)) {
                    videoReady = it
                }
            }
        }
        Image(

            painter = painterResource(id = R.drawable.bottom_background),
            contentDescription = null,
            modifier = Modifier.padding(top = 50.dp),
            contentScale = ContentScale.FillBounds

        )

    }
}

@Composable
fun ResultVideoPlayer(uri: Uri, videoReady: (Boolean) -> Unit) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            VideoView(context).apply {
                setMediaController(MediaController(context))
                setVideoURI(uri)
                requestFocus()
                start()
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        },
        update = {
            videoReady(true)
        }
    )
}

@Composable
fun UrlVideoTopBackground(navController: NavHostController) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.top_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            IconButton(onClick = {
                navController.navigate("Your model")
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp),
                    tint = DarkSpecStart
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
            Text(
                text = "ROLE MODEL",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Bold,
                color = DarkSpecEnd,

                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }

    }


}