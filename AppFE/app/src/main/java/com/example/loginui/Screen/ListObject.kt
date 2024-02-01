package com.example.loginui.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.ui.theme.interFontFamily

@SuppressLint("UnrememberedMutableState")
@Composable
fun ListObject(navController: NavHostController){
    var query by mutableStateOf("")

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val itemList = mutableStateListOf<String>()

        for (item in itemList) {
            Text(text = item,
                color = Color.Black,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
            )

        }
    }

}

