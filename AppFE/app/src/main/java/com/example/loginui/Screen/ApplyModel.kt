package com.example.loginui.Screen

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
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.viewinterop.AndroidView
@Preview
@Composable
fun UrlInputTextBox() {
    val isClick = remember { mutableStateOf(false) }
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
        if(isClick.value){
            VideoPlayer(url =url)
            url = ""
        }
    }
}


@Composable
fun VideoPlayer(url: String) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            VideoView(context).apply {
                setMediaController(MediaController(context))
                setVideoURI(Uri.parse(url))
                requestFocus()
                start()
            }
        }
    )
}
fun String.isValidUrl(): Boolean = this.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(this).matches()