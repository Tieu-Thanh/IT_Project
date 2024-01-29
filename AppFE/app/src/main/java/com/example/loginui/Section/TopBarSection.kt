package com.example.loginui.Section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.PurpleEnd
import com.example.loginui.ui.theme.PurpleStart
import com.example.loginui.ui.theme.interFontFamily


@Preview
@Composable
fun TopBarSection() {


    Text(
        modifier = Modifier

            .fillMaxWidth()
            .padding(16.dp),

        text = "ROLE MODEL",
        fontFamily = interFontFamily,
        fontSize = 30.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        color = Milk,
        textAlign = TextAlign.Center
    )
}

