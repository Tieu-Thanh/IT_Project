package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.ui.theme.interFontFamily

val itemList = mutableStateListOf<String>()
@SuppressLint("UnrememberedMutableState")
@Composable
fun ListObject(navController: NavHostController){
    Column {
        TextField()
        ListObjectDisplay()
        ButtonAtScreenBottom(navController)
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
            modifier = Modifier.alignByBaseline()
        ) {
            Text("Enter")
        }
    }
    if (existsMessage.isNotEmpty()) {
        ErrorMessage(existsMessage)
    }
}
@Composable
fun ErrorMessage(existsMessage: String){
    Text(text =existsMessage, style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = interFontFamily))
}
@Composable
fun ListObjectDisplay(){
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        for (item in itemList) {
            item {
                Text(text = item, style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = interFontFamily))
            }
        }
    }
}
fun isValueInList(value: String, list: List<String>): Boolean {
    return list.any { it.equals(value, ignoreCase = true) }
}
@Composable
fun ButtonAtScreenBottom(navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Button(
            onClick = {
                if (itemList.isNotEmpty()) {
                          navController.navigate("ModelInfo")
                      } else {
                          Toast.makeText(null, "Please enter at least one class", Toast.LENGTH_SHORT).show()
                      }
                  },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text("Next")
        }
    }
}