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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.PartyMode
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.loginui.R
import com.example.loginui.navigation.repo
import com.example.loginui.navigation.user
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.GoldSand
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.interFontFamily
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun UrlInputTextBox(navController: NavHostController, modelId: String) {
    val model = repo.getModel(modelId)
    Column(
        modifier = Modifier.background(Color.White)
    ) {

        val isClick = remember { mutableStateOf(false) }
        var videoUri by remember { mutableStateOf<Uri?>(null) }
        var videoReady by remember { mutableStateOf(false) }
        val context = LocalContext.current
        ApplyModelTopBackground(navController)
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(90.dp)
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Mail,
                        contentDescription = "Model Info",
                        modifier = Modifier
                            .size(25.dp)
                            .padding(end = 3.dp, top = 2.dp),
                        tint = GoldSand
                    )
                    Text(
                        text = "Email: $user",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.PartyMode,
                        contentDescription = "Model Info",
                        modifier = Modifier
                            .size(25.dp)
                            .padding(end = 3.dp, top = 2.dp),
                        tint = GoldSand
                    )
                    Text(
                        text = "Model: ${model.modelId}",

                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Model Info",
                        modifier = Modifier
                            .size(25.dp)
                            .padding(end = 3.dp, top = 2.dp),
                        tint = GoldSand
                    )
                    Text(
                        text = "Accuracy: ...",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }

        }


        var url by remember { mutableStateOf("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4") }
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(450.dp)
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                val isValidUrl = remember(url) { url.isValidUrl() }

                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Enter URL") },
                    isError = !isValidUrl,
                    singleLine = true,
                    modifier = Modifier.padding(bottom = 8.dp),
                    trailingIcon = {
                        if (url.isNotEmpty()) {
                            IconButton(onClick = {
                                isClick.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Clear text"
                                )
                            }
                        }
                    },
                )
                if (!isValidUrl) {
                    Text(
                        text = "Please enter a valid URL",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(
                    onClick = {
                        if (videoUri != null) {
                            Log.d(TAG, "uri case: $videoUri")
                            repo.postVideo(videoUri!!, modelId, null, context)
                        } else {
                            Log.d(TAG, "url case: $url")
                            Log.d(TAG, "UrlInputTextBox: $modelId")
                            repo.postVideo(null, modelId, url, context)
                            url = ""
                        }
                        videoReady = false
                    },
                    enabled = videoReady,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(TextColor1),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Start Calculate")
                }

                val pickVideoLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri: Uri? ->
                        videoUri = uri
                    }
                )

                Button(
                    onClick = {
                        pickVideoLauncher.launch("video/*")

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(TextColor1),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowUpward,
                        contentDescription = "UP",
                        tint = Milk
                    )
                }
                if (isClick.value) {
                    VideoPlayer(Uri.parse(url)) {
                        videoReady = it
                    }
                }
                if (videoUri != null) {
                    VideoPlayer(videoUri!!) {
                        videoReady = it
                    }
                }
            }
        }
        print(url)
        Button(
            onClick = {
                val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                navController.navigate("UrlVideoPopUp/$encodedUrl")
            }, modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(TextColor1),
            shape = RoundedCornerShape(50)
        ) {
            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = "UP", tint = Milk)
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
fun VideoPlayer(uri: Uri, videoReady: (Boolean) -> Unit) {
    val validYoutube = listOf("youtube.com", "youtu.be")
    var isYoutubeLink = false
    val localLifeCycle = LocalLifecycleOwner.current
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            validYoutube.forEach {
                if (uri.toString().contains(it)) {
                    isYoutubeLink = true
                }
            }
            when {
                isYoutubeLink -> {
                    val youtubeVideoId = extractYouTubeVideoIdFromShortUrl(uri.toString())
                    print(youtubeVideoId)
                    YouTubePlayerView(context = context).apply {
                        localLifeCycle.lifecycle.addObserver(this)
                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(youtubeVideoId, 0f)
                            }
                        })
                    }
                }

                else -> {
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
                }
            }
        },
        update = {
            videoReady(true)
        }
    )
}

fun extractYouTubeVideoIdFromShortUrl(url: String): String {
    val path = url.substringAfter("youtu.be/").substringAfter("watch?v=")
    return path.substringBefore('?').substringBefore('&')
}

fun String.isValidUrl(): Boolean =
    this.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(this).matches()

@Composable
fun ApplyModelTopBackground(navController: NavHostController) {
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
                navController.navigate("Your Model")
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