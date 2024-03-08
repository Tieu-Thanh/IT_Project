package com.example.loginui.SubScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.loginui.data.ModelResource
import com.example.loginui.navigation.repo

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserModels(navController: NavHostController){
    val modelList = remember{mutableStateListOf<ModelResource>()}
    Text(text = "Your model")
    val context = LocalContext.current
    LaunchedEffect(key1= true){
        repo.updateModelList().let {
            repo.setModel(it!!)
        }
        modelList.clear()
        modelList.addAll(repo.getModelList().models)
    }
    LazyColumn {
        items(modelList) { modelResource ->
            val backgroundColor = getColorByStatus(modelResource.status!!)
            Column(modifier = Modifier
                .padding(16.dp)
                .background(backgroundColor)
                .clickable {
                    when (modelResource.status) {
                        0 -> {
                            Toast
                                .makeText(context, "Data is not ready yet", Toast.LENGTH_SHORT)
                                .show()
                        }
                        1 -> {
                            repo.trainModel(modelResource.modelId)
                            Toast
                                .makeText(context, "Start Training", Toast.LENGTH_SHORT)
                                .show()
                        }

                        2 -> Toast
                            .makeText(context, "Model is training", Toast.LENGTH_SHORT)
                            .show()

                        3 -> navController.navigate("ApplyModel/${modelResource.modelId}")
                    }
                }
            ) {
                Text(text = "Model ID: ${modelResource.modelId}")
                Text(text = "User ID: ${modelResource.userId}")
                Text(text = "Model Name: ${modelResource.modelName}")
                Text(text = "Classes: ${modelResource.classes.joinToString(", ")}")
                Text(text = "Crawl Number: ${modelResource.crawlNumber}")
                Text(text = "Status: ${modelResource.status}")
                modelResource.createdAt?.let {
                    Text(text = "Created At: $it")
                }
            }
        }
    }

}
@Composable
fun getColorByStatus(status: Int): Color {
    return when (status) {
        0 -> Color.Gray
        1 -> Color.Yellow
        2 -> Color.Red
        3 -> Color.Green
        else -> {
            Color.Black
        }
    }

}
