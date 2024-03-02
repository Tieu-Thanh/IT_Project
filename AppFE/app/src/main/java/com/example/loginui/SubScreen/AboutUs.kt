package com.example.loginui.SubScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.Screen.TopBackground
import com.example.loginui.Screen.getGradient
import com.example.loginui.data.ModelResource
import com.example.loginui.navigation.repo
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.JaipurEnd
import com.example.loginui.ui.theme.JaipurStart
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.SpecCyan
import com.example.loginui.ui.theme.SpecPink
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.interFontFamily


@SuppressLint("UnrememberedMutableState")
@Composable
fun About_us(navController: NavHostController) {
    val modelList = remember { mutableStateListOf<ModelResource>() }
    Column {
        TopBackground2(navController)
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = MaterialTheme.shapes.medium
                )

        ) {
            Row {
                Icon(
                    imageVector = Icons.Rounded.Circle,
                    contentDescription = "Model Info",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 3.dp, top = 15.dp),
                    tint = TextColor1
                )
                Text(
                    text = "Your Model",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
        repo.getModelList {
            modelList.addAll(it.models)
        }
        ModelResourceList(modelList)
        Log.d("11111111111", "About_us: $modelList")
    }


}

@Composable
fun ModelResourceList(modelResources: List<ModelResource>) {
    LazyColumn {
        items(modelResources) { modelResource ->
            ModelResourceItem(modelResource)
        }
    }
}


@Composable
fun ModelResourceItem(modelResource: ModelResource) {

    Spacer(modifier = Modifier.height(12.dp))
    Box(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(brush = getGradient(SpecPink, SpecCyan))

            .fillMaxWidth()
            .clickable {

            }
            .border(
            width = 2.dp,
            color = Color.Black,
            shape = RoundedCornerShape(20.dp)
        )
            .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Model ID: ${modelResource.modelId}",
                color = Milk,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "User ID: ${modelResource.userId}",
                color = Milk,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Model Name: ${modelResource.modelName}",
                color = Milk,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Classes: ${modelResource.classes.joinToString(", ")}",
                color = Milk,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Crawl Number: ${modelResource.crawlNumber}",
                color = Milk,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            modelResource.createdAt?.let {
                Text(
                    text = "Created At: $it",
                    color = Milk,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

@Composable
fun TopBackground2(navController: NavHostController) {
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
            text = "ROLE MODEL",
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            color = DarkSpecEnd,

            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}
