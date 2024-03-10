package com.example.loginui.Screen

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.loginui.R
import com.example.loginui.navigation.repo
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.interFontFamily
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun UrlVideoPopUp(navController: NavHostController, url: String) {
    var videoUri by remember { mutableStateOf<Uri?>(null) }
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
                VideoPlayer1(Uri.parse(url)) {
                    videoReady = it
                }
                Log.d(TAG, "UrlVideoPopUp: "+url)
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
fun VideoPlayer1(uri: Uri, videoReady: (Boolean) -> Unit) {
    val localLifeCycle = LocalLifecycleOwner.current
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

fun extractYouTubeVideoIdFromShortUrl1(url: String): String {
    val path = url.substringAfter("youtu.be/").substringAfter("watch?v=")
    return path.substringBefore('?').substringBefore('&')
}

fun String.isValidUrl1(): Boolean =
    this.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(this).matches()

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