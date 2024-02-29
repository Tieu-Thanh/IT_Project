package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.PartyMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.loginui.data.ModelResource
import com.example.loginui.navigation.repo
import com.example.loginui.navigation.user
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.GoldSand
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.WhiteColor
import com.example.loginui.ui.theme.interFontFamily
import java.time.Instant
import java.util.Base64

val TAG = "ModelInfo"
val usersImages = mutableListOf<Bitmap?>()

@Preview
@SuppressLint("MutableCollectionMutableState", "InvalidColorHexValue")
@Composable
fun ModelInfo() {
    var loading by remember {
        mutableStateOf(false)
    }
    var uploadComplete by remember {
        mutableStateOf("Upload more image to improve the accuracy")
    }

    var isVisible by remember { mutableStateOf(true) }
    val bitmapList by remember { mutableStateOf(mutableStateListOf<Bitmap?>()) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    val takePicturePreviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            imageBitmap = bitmap
            usersImages.add(bitmap)
        }
    )
    val context = LocalContext.current
    var sizeOfDefaultDataset by remember { mutableStateOf("0") }

    selectedImage?.let {
        if (isVisible) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .zIndex(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .absoluteOffset(0.dp, 50.dp)
                        .padding(16.dp)
                        .zIndex(2f)
                )
                {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(400.dp)
                            .shadow(300.dp, ambientColor = Color.Black)
                            .clickable { isVisible = false },
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.background(color = WhiteColor)
    ) {
        ModelTopBackground()

        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(start = 6.dp, end = 12.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = MaterialTheme.shapes.medium
                )

        ) {
            Row(


            ) {
                Icon(
                    imageVector = Icons.Rounded.Circle,
                    contentDescription = "Model Info",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 3.dp, top = 15.dp),
                    tint = TextColor1
                )
                Text(
                    text = "Model Info",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }

        Box(
            modifier = Modifier

                .padding(start = 6.dp, top = 8.dp, end = 12.dp)
                .height(90.dp)
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column() {
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
                        text = "Email: $user",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
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
                        text = "Model: Yolov8",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
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
                        text = "Accuracy: ...",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }


        }


        LazyRow {
            items(itemList) { item ->
                Button(onClick = {},
                    modifier = Modifier.padding(start = 8.dp, top = 10.dp),
                    colors = ButtonDefaults.buttonColors(TextColor1)) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = item, color = Milk)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            OutlinedTextField(
                value = sizeOfDefaultDataset,
                onValueChange = { newText ->
                    if (newText.all { it.isDigit() }) {
                        sizeOfDefaultDataset = newText
                    }
                },
                label = { Text("Size") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.2f)
            )
            Spacer(modifier = Modifier.weight(1f).fillMaxWidth())
            Button(
                onClick = {
                    takePicturePreviewLauncher.launch(null)
                },
                modifier = Modifier.padding( top = 10.dp, end = 12.dp),

                colors = ButtonDefaults.buttonColors(TextColor1)

            ) {
                Icon(
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Camera",
                    tint = Milk
                )
            }
        }

        if (imageBitmap != null) {
            bitmapList.add(imageBitmap)
            imageBitmap = null
        }
        Row(
            modifier = Modifier
                .size(400.dp)
                .padding(start = 8.dp, top = 10.dp, end = 12.dp, bottom = 10.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {
            bitmapList.forEach {
                it?.asImageBitmap()?.let { bitmap ->
                    Image(bitmap = bitmap, contentDescription = "Image", modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            selectedImage = bitmap.asAndroidBitmap()
                            isVisible = true
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                loading = true
                uploadFunction(sizeOfDefaultDataset.toInt(), context)
            },
            Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(TextColor1),
            shape = RoundedCornerShape(50)

        ) {
            Text(text = "Upload")
        }
        Text(
            text = uploadComplete,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )

    }
    if (loading) {
        Box(modifier = Modifier.size(200.dp).zIndex(1f), contentAlignment = Alignment.Center) {
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
}

fun uploadFunction(sizeOfDefaultDataset: Int, context: Context) {
    repo.postModelInfo(
        "Yolov8",
        itemList,
        sizeOfDefaultDataset,
        usersImages.toList().requireNoNulls(),
        context
    )
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

@Composable
fun ModelTopBackground() {

    Row(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        IconButton(onClick = {
//                navController.navigate("ListObject")
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