package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.interFontFamily

val itemList = mutableStateListOf<String>()

@Preview
@SuppressLint("UnrememberedMutableState")
@Composable
fun ListObject(navController: NavHostController) {
    Column {
        TopBackground(navController)
        TextField()
        ListObjectDisplay()
        ButtonAtScreenBottom(navController)
    }
}


@Composable
fun TopBackground(navController: NavHostController) {
    Row(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        IconButton(onClick = {
//            navController.navigate("HomeScreen")
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun TextField() {
    var existsMessage by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }
    Row(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(value = query, onValueChange = {
            query = it
        }, label = {
            Text(
                text = "Class",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = interFontFamily
                )
            )
        },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .focusable(true)
        )
        Button(
            onClick = {
                if (query.isNotEmpty()) {
                    existsMessage = if (isValueInList(query, itemList)) {
                        "This class already exists"
                    } else {
                        itemList.add(query)
                        ""
                    }
                }
            },
            modifier = Modifier

                .alignByBaseline()
                .padding(top = 12.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(TextColor1)
        ) {
            Text(
                text = "Enter",
                style = TextStyle(
                    color = Milk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = interFontFamily
                )
            )
        }
    }
    if (existsMessage.isNotEmpty()) {
        ErrorMessage(existsMessage)
    }
}

@Composable
fun ErrorMessage(existsMessage: String) {
    Text(
        text = existsMessage,
        style = TextStyle(
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = interFontFamily
        )
    )
}

@Composable
fun ListObjectDisplay() {
    Box(
        modifier = Modifier
            .padding(start = 8.dp, top = 10.dp, end = 8.dp)
            .fillMaxWidth()
            .height(610.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = MaterialTheme.shapes.medium
            )

    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            for (item in itemList) {
                item {
                    Text(
                        text = item,
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            fontFamily = interFontFamily
                        )
                    )
                }
            }
        }


    }

}

fun isValueInList(value: String, list: List<String>): Boolean {
    return list.any { it.equals(value, ignoreCase = true) }
}

@Composable
fun ButtonAtScreenBottom(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
    ) {

        Button(
            onClick = {
                if (itemList.isNotEmpty()) {
                          navController.navigate("ModelInfo")
                } else {
                    Toast.makeText(null, "Please enter at least one class", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(TextColor1)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Foward",
                modifier = Modifier.size(30.dp),
                tint = Milk
            )
        }
    }
}