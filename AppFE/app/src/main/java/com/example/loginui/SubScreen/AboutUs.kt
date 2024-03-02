package com.example.loginui.SubScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginui.data.ModelResource
import com.example.loginui.navigation.repo

@SuppressLint("UnrememberedMutableState")
@Composable
fun About_us(navController: NavHostController){
    val modelList = remember{mutableStateListOf<ModelResource>()}
    Text(text = "Your model")

    LazyColumn {
        items(modelList) { modelResource ->
            Column(modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.navigate("ApplyModel/${modelResource.modelId}")
                }
            ) {
                Text(text = "Model ID: ${modelResource.modelId}")
                Text(text = "User ID: ${modelResource.userId}")
                Text(text = "Model Name: ${modelResource.modelName}")
                Text(text = "Classes: ${modelResource.classes.joinToString(", ")}")
                Text(text = "Crawl Number: ${modelResource.crawlNumber}")
                modelResource.createdAt?.let {
                    Text(text = "Created At: $it")
                }
            }
        }
    }

}