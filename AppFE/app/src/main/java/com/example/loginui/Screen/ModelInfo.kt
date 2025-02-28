package com.example.loginui.Screen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.loginui.navigation.repo
import com.example.loginui.ui.theme.DarkSpecEnd
import com.example.loginui.ui.theme.DarkSpecStart
import com.example.loginui.ui.theme.GoldSand
import com.example.loginui.ui.theme.Milk
import com.example.loginui.ui.theme.TextColor1
import com.example.loginui.ui.theme.WhiteColor
import com.example.loginui.ui.theme.interFontFamily


val TAG = "ModelInfo"
val usersImages = mutableListOf<Bitmap?>()


@SuppressLint(
    "MutableCollectionMutableState", "InvalidColorHexValue",
    "UnusedBoxWithConstraintsScope"
)

@Composable
fun ModelInfo(navController: NavHostController) {
    var loading by remember {
        mutableStateOf(false)
    }
    var uploadComplete by remember {
        mutableStateOf("Upload more image to improve the accuracy")
    }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isVisible by remember { mutableStateOf(true) }
    val bitmapList by remember { mutableStateOf(mutableStateListOf<Bitmap?>()) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    var modelId by remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }
    val takeImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                imageUri
            )
            imageBitmap = bitmap
            usersImages.add(imageBitmap)
        }
    }


    var sizeOfDefaultDataset by remember { mutableStateOf("0") }
    fun createImageUri(context: Context): Uri {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "img${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }
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
    if (loading) {
        BoxWithConstraints(
            modifier = Modifier
                .size(200.dp)
                .zIndex(1f), contentAlignment = Alignment.Center
        ) {
            ComposableProcessBar(
                percentage = 1.0f, number = 100,
                onAnimationEnd = {
                    if (!it) {
                        loading = false
                        uploadComplete = "Upload Complete"
                    }
                }
            )
        }
    }
    Column(
        modifier = Modifier.background(color = WhiteColor)
    ) {
        ModelTopBackground(navController)
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
            Row(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = modelId,
                    onValueChange = { newName ->
                        // Update the state with the new name
                        modelId = newName
                    },
                    label = {
                        Text(
                            text = "Model name",
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                fontFamily = interFontFamily
                            )
                        )
                    }, // Label displayed on the text field

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 4.dp, end = 4.dp)
                        .focusable(true),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Gray, // Optional: also make the border transparent when the field is focused
                        // You can also adjust other colors like background, cursor color as needed
                        unfocusedBorderColor = Color.Black, // Makes border transparent
                    ),
                    singleLine = true,
                    textStyle = (
                            LocalTextStyle.current.copy(textAlign = TextAlign.Start)
                            ),

                    )

            }

        }


        LazyRow {

            items(itemList) { item ->
                Button(
                    onClick = {},
                    modifier = Modifier.padding(start = 8.dp, top = 10.dp),
                    colors = ButtonDefaults.buttonColors(TextColor1)
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = item, color = Milk)
                }

            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()

        ) {
            OutlinedTextField(
                value = sizeOfDefaultDataset,
                onValueChange = { newText ->
                    if (newText.all { it.isDigit() } && newText.length <= 2) {
                        sizeOfDefaultDataset = newText
                    }
                },
                label = { Text("Size") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.2f)
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Button(

                onClick = {
                    imageUri = createImageUri(context)
                    imageUri.let {
                        takeImage.launch(it)
                    }
                },
                modifier = Modifier.padding(top = 10.dp, end = 12.dp),

                colors = ButtonDefaults.buttonColors(TextColor1)

            ) {
                Icon(
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Camera",
                    tint = Milk
                )
            }

            if (imageBitmap != null) {
                bitmapList.add(imageBitmap)
                imageBitmap = null
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.8f)
                .padding(start = 8.dp, top = 10.dp, end = 12.dp, bottom = 10.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
        ) {
            bitmapList.forEach {
                it?.asImageBitmap()?.let { bitmap ->
                    Image(bitmap = bitmap, contentDescription = "Image", modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
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
                if (isVisible) {
                    loading = true
                    if (modelId.isNotEmpty() && sizeOfDefaultDataset.isNotEmpty() && sizeOfDefaultDataset.toInt() > 0) {
                        uploadFunction(sizeOfDefaultDataset.toInt(), context, modelId) {
                            success = it
                        }
                    } else {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
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
}

fun uploadFunction(
    sizeOfDefaultDataset: Int,
    context: Context,
    modelId: String,
    success: (Boolean) -> Unit
) {

    repo.postModelInfo(
        modelId,
        "Yolov8",
        itemList,
        sizeOfDefaultDataset,
        usersImages.toList().requireNoNulls(),
        context
    ){
        if (!it)
            success(true)

    }

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
fun ModelTopBackground(navController: NavHostController) {

    Row(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        IconButton(onClick = {
            navController.navigate("ListObject")
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