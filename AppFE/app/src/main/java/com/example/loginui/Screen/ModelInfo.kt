package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.DeveloperMode
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.ModelTraining
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.loginui.navigation.user
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.interFontFamily


val TAG = "ModelInfo"

@Preview
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ModelInfo() {
    var loading by remember {
        mutableStateOf(false)
    }
    var uploadComplete by remember {
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
        TopBackground()
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Circle,
                contentDescription = "Model Info",
                tint = TextColor1,
                modifier = Modifier
                    .size(15.dp)
                    .padding(start = 5.dp)

            )
            Text(
                text = "Model Info",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 5.dp, end = 10.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = MaterialTheme.shapes.medium
                )

        ) {
            Column {

                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Mail,
                        contentDescription = "Model Info",
                        tint = DarkSpecEnd,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "Email: $user",
                        modifier = Modifier.padding(top = 2.dp, start = 8.dp),
                        color = DarkSpecEnd,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DeveloperMode,
                        contentDescription = "Model Info",
                        tint = DarkSpecEnd,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "Model: Yolov8",
                        modifier = Modifier.padding(top = 1.dp, start = 8.dp),
                        fontSize = 20.sp,
                        color = DarkSpecEnd,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Model Info",
                        tint = DarkSpecEnd,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "Accuracy: ...",
                        modifier = Modifier.padding(top = 1.dp, start = 8.dp, bottom = 4.dp),
                        fontSize = 20.sp,
                        color = DarkSpecEnd,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }




        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Circle,
                contentDescription = "Important",
                tint = TextColor1,
                modifier = Modifier
                    .size(15.dp)
                    .padding(start = 5.dp)
            )
            Text(
                text = "Training place",
                modifier = Modifier.padding(start = 5.dp, top = 2.dp, end = 120.dp),
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    takePicturePreviewLauncher.launch(null)

                },
                colors = ButtonDefaults.buttonColors(TextColor1)
            )

            {
                Icon(
                    imageVector = Icons.Rounded.Camera,
                    contentDescription = "Camera",
                    tint = Milk
                )
            }
        }

        if (imageBitmap != null) {
            bitmapList.add(imageBitmap)
            imageBitmap = null
        }
        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, end = 10.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Row(modifier = Modifier.size(200.dp)) {
                bitmapList.forEach {
                    it?.asImageBitmap()?.let { bitmap ->
                        Image(bitmap = bitmap, contentDescription = "Image")
                    }
                }
            }
        }

        Button(
            onClick = {
                loading = true
            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp),
            colors = ButtonDefaults.buttonColors(TextColor1)
        ) {
            Text(text = "Upload")
        }
        Text(
            text = uploadComplete,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)
        )
        if (loading) {
            Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                ComposableProcessBar(
                    percentage = 1.0f, number = 100,
                    onAnimationEnd = {
                        if (!it) {
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
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(TextColor1)
        ) {
            Text("Start Training Your Model")
        }
    }
}

fun uploadFunction() {

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
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animDelay
        ), label = ""
    )
    LaunchedEffect(currentPercentage.value) {
        animationPlayed = true
        if (currentPercentage.value == percentage) {
            animationPlayed = false
        }
        onAnimationEnd(animationPlayed)
    }
    Box(
        modifier = Modifier.size(radius * 2f),
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (currentPercentage.value * number).toInt().toString(),
            fontSize = fontSize,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
