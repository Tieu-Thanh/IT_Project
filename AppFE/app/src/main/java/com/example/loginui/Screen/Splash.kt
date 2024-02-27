package com.example.loginui.Screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.PurpleEnd
import com.example.loginui.ui.theme.PurpleStart
import com.example.loginui.ui.theme.interFontFamily
import kotlinx.coroutines.delay


@Composable
fun AnimatedSplash(navController: NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.navigate("SignIn")
    }
    Splash(alpha = alphaAnim.value)
}


@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(brush = getGradient(PurpleStart, PurpleEnd))
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha = alpha),
                imageVector = Icons.Rounded.Memory,
                contentDescription = "Logo",
                tint = Milk
            )
            Text(
                modifier = Modifier.alpha(alpha = alpha),
                text = "ROLE MODEL",
                fontFamily = interFontFamily,
                fontSize = 30.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = Milk,
                textAlign = TextAlign.Justify
            )
        }

    }
}

@Composable
@Preview
fun SplashScreenView() {
    Splash(alpha = 1f)
}

fun getGradient(
    startColor: Color,
    endColor: Color,

): Brush {
    return Brush.verticalGradient(
        colors = listOf(startColor, endColor)
    )
}