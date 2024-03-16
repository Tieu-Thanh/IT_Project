package com.example.loginui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.example.loginui.Screen.VideoPlayer
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.interFontFamily

class DisplayVideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val outputUrl = intent.getStringExtra("url")!!
        val outputCount = intent.getIntExtra("count_result", 0)
        setContent {
            UrlVideoPopUp(outputUrl, outputCount)
        }
    }

}
@Composable
fun UrlVideoPopUp(url:String, outputCount:Int) {
    val videoUri by remember { mutableStateOf<Uri?>(null) }
    var videoReady by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        UrlVideoTopBackground()
        Spacer(modifier = Modifier.height(35.dp))
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(400.dp)
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()) {
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
        Text(
            text = "OC: $outputCount",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp // Kích thước font lớn
            ),
            modifier = Modifier.padding(16.dp) // Padding xung quanh Text
        )
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
        modifier = Modifier
            .fillMaxWidth(),
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
fun UrlVideoTopBackground() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.top_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
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