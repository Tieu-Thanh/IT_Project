package com.example.loginui.SubScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.PartyMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.R
import com.example.loginui.data.ModelResource
import com.example.loginui.navigation.repo
import com.example.loginui.navigation.user
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.GoldSand
import com.example.loginui.ui.theme.interFontFamily

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserModels(navController: NavHostController) {
    val modelList = remember { mutableStateListOf<ModelResource>() }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        repo.updateModelList().let {
            repo.setModel(it!!)
        }
        modelList.clear()
        modelList.addAll(repo.getModelList().models)
    }
    Column {
        UserModelsTopBackground(navController)
        LazyColumn {
            items(modelList) { modelResource ->


                Column(modifier = Modifier
                    .padding(16.dp)

                    .clickable {
//                    when (modelResource.status) {
//                        0 -> {
//                            Toast
//                                .makeText(context, "Data is not ready yet", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                        1 -> {
//                            repo.trainModel(modelResource.modelId)
//                            Toast
//                                .makeText(context, "Start Training", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//
//                        2 -> Toast
//                            .makeText(context, "Model is training", Toast.LENGTH_SHORT)
//                            .show()

//                        3 ->
                        navController.navigate("ApplyModel/${modelResource.modelId}")
//                    }
                    }
                ) {

                    Box(
                        modifier = Modifier

                            .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                            .height(200.dp)
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
                                    text = "Model ID: ${modelResource.modelId}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp
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
                                    text = "User ID: ${modelResource.userId}",

                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp
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
                                    text = "Model Name: ${modelResource.modelName}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp
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
                                    text = "Classes: ${modelResource.classes.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp
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
                                    text = "Crawl Number: ${modelResource.crawlNumber}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp
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
                                    text = "Status: ${modelResource.status}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 17.sp
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
                                modelResource.createdAt?.let {
                                    Text(
                                        text = "Created At: $it",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 17.sp
                                    )
                                }
                            }


                        }

                    }


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

@Composable
fun UserModelsTopBackground(navController: NavHostController) {
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
                navController.navigate("HomeScreen")
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
                text = "YOUR MODEL",
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Bold,
                color = DarkSpecEnd,

                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }

    }


}
