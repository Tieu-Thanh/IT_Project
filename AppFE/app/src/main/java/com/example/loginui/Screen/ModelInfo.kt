package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginui.navigation.user


val TAG = "ModelInfo"
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ModelInfo(){
    var loading by remember {
        mutableStateOf(false)
    }
    var uploadComplete by  remember {
        mutableStateOf("Upload more image to improve the accuracy")
    }
    val bitmapList by remember { mutableStateOf(mutableStateListOf<Bitmap?>()) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val takePicturePreviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            imageBitmap = bitmap
        }
    )
    Column {
        Text(text ="Model Info", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = "Email: $user")
        Text(text = "Model: Yolov8")
        Text(text = "Accuracy: ...")
        Button(onClick = {
            takePicturePreviewLauncher.launch(null)
        }) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = "Camera")
        }
        if (imageBitmap != null){
            bitmapList.add(imageBitmap)
            imageBitmap = null
        }
        Row(modifier = Modifier.size(200.dp)){
            bitmapList.forEach {
                it?.asImageBitmap()?.let { bitmap ->
                    Image(bitmap = bitmap, contentDescription = "Image")
                }
            }
        }
        Button(onClick = {
            loading = true
        }
        ) {
            Text(text = "Upload")
        }
        Text(text =uploadComplete, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (loading){
            Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center){
                ComposableProcessBar(
                    percentage = 1.0f, number = 100,
                    onAnimationEnd = {
                        if(!it){
                            loading = false
                            uploadComplete = "Upload complete"
                        }
                    }
                )
            }
        }
        Button(
            onClick = { //TODO: Start training model
                },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Start Training Your Model")
        }
    }
}

fun uploadFunction(){

}

@Composable
fun ComposableProcessBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp,
    animationDuration: Int = 1000,
    animDelay: Int = 0,
    onAnimationEnd: (Boolean) -> Unit = {}
){
    var animationPlayed by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animDelay
        ), label = ""
    )
    LaunchedEffect(currentPercentage.value){
        animationPlayed = true
        if (currentPercentage.value == percentage){
            animationPlayed = false
        }
        onAnimationEnd(animationPlayed)
    }
    Box(
        modifier = Modifier.size(radius * 2f),
    ){
        Canvas(modifier = Modifier.size(radius*2f) ){
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(),cap = StrokeCap.Round)
            )
        }
        Text(text = (currentPercentage.value * number).toInt().toString(),
            fontSize = fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}