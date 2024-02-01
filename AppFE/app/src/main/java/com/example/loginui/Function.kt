package com.example.loginui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.loginui.Screen.getGradient
import com.example.loginui.data.FuncItem
import com.example.loginui.navigation.user
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.JaipurEnd
import com.example.loginui.ui.theme.JaipurStart
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.interFontFamily

val func_items = listOf(
    FuncItem(
        title = "Count object",
        description = "This solution will help you to count many object",

        ),
    FuncItem(
        title = "Distance measurement",
        description = "This solution will show you the approximately of distance",
        )
    )


@Composable
fun FunctionSection(navController: NavHostController) {
    LazyColumn {
        items(func_items.size) { index ->
            FuncInfo(index, navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun FuncInfo(
    index: Int,
    navController: NavHostController
) {

    val func = func_items[index]
    var lastItemPaddingEnd = 0.dp
    if (index == func_items.size - 1) {
        lastItemPaddingEnd = 16.dp
    }

    var image = painterResource(id = R.drawable.count_object)
    if (func.title == "Distance measurement") {
        image = painterResource(id = R.drawable.distancemeasurement)
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(brush = getGradient(JaipurStart, JaipurEnd))
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable {
                    if (func.title == "Count object") {
                        Log.d("Function", "FuncInfo: $user")
                        navController.navigate("ListObject")
                    } else if (func.title == "Distance measurement") {
                        navController.navigate("DM_function")
                    }
                }
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Image(
                painter = image,
                contentDescription = func.description,
                modifier = Modifier
                    .width(70.dp)
                    .padding(top = 5.dp)
                    .fillMaxSize()

            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Row {
                    Text(
                        text = func.title,
                        color = Milk,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Foward",
                        modifier = Modifier
                            .padding(top = 4.dp, start = 5.dp),
                        tint = Color.Yellow
                    )
                }
                Row {
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = "Foward"
                    )
                    Text(
                        text = func.description,
                        color = DarkSpecStart,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

            }


        }

    }
}



