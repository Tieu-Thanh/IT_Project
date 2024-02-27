package com.example.loginui.Screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.net.Uri
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.example.loginui.navigation.repo

@Composable
fun UrlInputTextBox(navController: NavHostController) {
    val isClick = remember { mutableStateOf(false) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var videoReady by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp)) {
        var url by remember { mutableStateOf("") }
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
                        imageVector = Icons.Filled.Clear,
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
              repo.postURL(url,"")
            },
            enabled = videoReady
        ) {
            Text("Start Calculate")
        }

        val pickVideoLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                videoUri = uri
            }
        )

        Button(onClick = { pickVideoLauncher.launch("video/*") }) {
            Text("Pick Video")
        }
        if(isClick.value){
            VideoPlayer(Uri.parse(url)){
                videoReady = it
            }
            url = ""
        }
        if (videoUri != null) {
            VideoPlayer(videoUri!!){
                videoReady = it
            }
        }
    }
}


@Composable
fun VideoPlayer(uri: Uri, videoReady:(Boolean)->Unit) {
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
fun String.isValidUrl(): Boolean = this.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(this).matches()